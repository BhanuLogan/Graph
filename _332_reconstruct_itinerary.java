/*
    Problem: https://leetcode.com/problems/reconstruct-itinerary
    Companies: Google, Facebook, Uber, Bloomberg, Snapchat, Netflix, Amazon, Twitter, Pinterest, Yahoo
    Topics: Graph, DFS, Euler Circuit 
*/

class Airport {
    String city;
    PriorityQueue<Airport> routes;

    Airport(String city) {
        this.city = city;
        this.routes = new PriorityQueue<>((a, b) -> a.compare(b));
    }

    void addAirport(Airport airport) {
        routes.add(airport);
    }
    
    boolean hasRoutes() {
        return !routes.isEmpty();
    }

    Airport next() {
        return routes.poll();
    }

    int compare(Airport b) {
        return this.city.compareTo(b.city);
    }
 }

class Solution {
    Map<String, Airport> airports;
    
    Solution() {
        this.airports = new HashMap<>();
    }

    public List<String> findItinerary(List<List<String>> tickets) {
        create(tickets);
        List<String> path = new ArrayList<>();
        travel(airports.get("JFK"), path);
        return path;
    }

    private void travel(Airport airport, List<String> path) {
        while(airport.hasRoutes()) {
            Airport next = airport.next();
            travel(next, path);   
        }
        path.addFirst(airport.city);
    }

    private void create(List<List<String>> tickets) {
        Set<String> cities = new HashSet<>();
        
        for(List<String> ticket : tickets) {
            String from = ticket.get(0), to = ticket.get(1);
            cities.add(from);
            cities.add(to);
        }

        for(String city : cities) {
            airports.put(city, new Airport(city));
        }

        for(List<String> ticket : tickets) {
            String from = ticket.get(0), to = ticket.get(1);
            Airport fromAirport = airports.get(from);
            Airport toAirport = airports.get(to);
            fromAirport.addAirport(toAirport);
        }
    }
}
