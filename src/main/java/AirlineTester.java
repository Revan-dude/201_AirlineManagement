import enums.BookingClass;
import enums.FlightStatus;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import pojos.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AirlineTester {

    public Airline read() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Airline.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Airline airline = (Airline) unmarshaller.unmarshal(AirlineTester.class.getResourceAsStream("airline.xml"));

        return airline;
    }

    public void write(Airline airline, String fileName) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(Airline.class);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(airline, new File(Path.of(System.getProperty("user.dir"), "src", "main", "resources", fileName).toUri()));
    }

    public List<Flight> flightsSorted(Airline airline) {

        return airline.getFlights().stream()
                .sorted(Comparator.comparing(Flight::getDeparture))
                .collect(Collectors.toList());
    }

    public List<Flight> flightsFrom(Airline airline, String airportCode) {

        Airport airport = airline.getAirports().stream()
                .filter(a -> a.getCode().equals(airportCode))
                .findFirst()
                .get();

        return airline.getFlights().stream()
                .filter(f -> f.getOrigin().equals(airport))
                .collect(Collectors.toList());
    }

    public List<Passenger> passengersOn(Airline airline, String flightNumber) {

        Flight flight = airline.getFlights().stream()
                .filter(f -> f.getNumber().equals(flightNumber))
                .findFirst()
                .get();

        return airline.getBookings().stream()
                .filter(b -> b.getFlight().equals(flight))
                .map(Booking::getPassenger)
                .collect(Collectors.toList());
    }

    public Map<BookingClass, Long> bookingsByClass(Airline airline) {

        return airline.getBookings().stream()
                .collect(Collectors.groupingBy(Booking::getBookingClass, Collectors.counting()));
    }

    public Map<Airport, Long> departuresByAirport(Airline airline) {
        return airline.getBookings().stream()
                .collect(Collectors.groupingBy(b -> b.getFlight().getOrigin(), Collectors.counting()));
    }

    public List<Airport> airportsSortedByDepartures(Airline airline) {
        return airline.getAirports().stream()
                .sorted(Comparator.comparing(a -> departuresByAirport(airline).getOrDefault(a, 0L)))
                .collect(Collectors.toList());
    }

    public double occupancy(Airline airline, Flight flight) {
        return (double) airline.getBookings().stream()
                .filter(b -> b.getFlight().equals(flight))
                .count() / flight.getAircraft().getCapacity();
    }

    public void calculateOccupancy(Airline airline) {
        airline.getFlights().forEach(f -> {
            double numOfBookings = airline.getBookings().stream()
                    .filter(b -> b.getFlight().equals(f))
                    .count();

            int capacity = f.getAircraft().getCapacity();

            f.setOccupancy(capacity / numOfBookings);
        });
    }

    public List<Flight> highlyOccupiedFlights(Airline airline, double occupancyThreshold) {
        return airline.getFlights().stream()
                .filter(f -> f.getOccupancy() > occupancyThreshold)
                .sorted(Comparator.comparing(Flight::getOccupancy))
                .collect(Collectors.toList());
    }

    public void update(Airline airline) {
        Passenger passenger = Passenger.builder()
                .id("PA09")
                .firstName("Noah")
                .lastName("Kern")
                .birthDate(LocalDate.of(1999, 5, 16))
                .preferences(List.of("window", "vegetarian"))
                .build();

        airline.getPassengers().add(passenger);

        Booking booking = Booking.builder()
                .passenger(passenger)
                .flight(airline.getFlights().stream()
                        .filter(f -> f.getNumber().equals("OS102"))
                        .findFirst()
                        .get())
                .bookingClass(BookingClass.ECONOMY)
                .number("B021")
                .seat("Seat 2B")
                .price(BigDecimal.valueOf(189.90))
                .build();

        airline.getBookings().add(booking);

        airline.getBookings().stream()
                .filter(b -> b.getNumber().equals("B011"))
                .findFirst()
                .ifPresent(b -> b.setPrice(BigDecimal.valueOf(179.50)));

        airline.getFlights().stream()
                .filter(f -> f.getStatus().equals(FlightStatus.CANCELLED))
                .forEach(f -> {
                    airline.getBookings().removeIf(b -> b.getFlight().equals(f));
                });

        calculateOccupancy(airline);
    }



    public static void main(String[] args) throws JAXBException, IOException {
        AirlineTester tester = new AirlineTester();

        Airline airline = new Airline();
        try {
            airline = tester.read();
            System.out.println(airline);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        tester.calculateOccupancy(airline);


        System.out.println("================== All flights sorted by departure ==================");
        tester.flightsSorted(airline).forEach(System.out::println);

        System.out.println("================== All flights from London ==================");
        tester.flightsFrom(airline, "LHR").forEach(System.out::println);

        System.out.println("================== All passengers on flight OS102 ==================");
        tester.passengersOn(airline, "OS102").forEach(System.out::println);

        System.out.println("================== Bookings by class ==================");
        tester.bookingsByClass(airline).forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.println("================== Departures by airport ==================");
        tester.departuresByAirport(airline).forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.println("================== Airports sorted by departures ==================");
        tester.airportsSortedByDepartures(airline).forEach(System.out::println);

        System.out.println("================== Occupancy of flight OS102 ==================");
        System.out.println(tester.occupancy(airline, airline.getFlights().stream()
                .filter(f -> f.getNumber().equals("OS102"))
                .findFirst()
                .get()));

        System.out.println("================== Highly occupied flights ==================");
        tester.highlyOccupiedFlights(airline, 0.8).forEach(System.out::println);

        System.out.println("================== Updated airline ==================");
        tester.update(airline);
        System.out.println(airline);

        tester.write(airline, "updated.xml");
    }
}
