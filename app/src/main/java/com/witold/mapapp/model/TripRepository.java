package com.witold.mapapp.model;

import com.witold.mapapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TripRepository {
    private HashMap<String, Place> placeHashMap;
    private HashMap<String, Trip> tripHashMap;

    public TripRepository() {
        this.placeHashMap = new HashMap<>();
        placeHashMap.put("Pręgież", new Place("Pręgież", "Pręgież - miejsce spotkań i serce wrocławskiego rynku", R.drawable.place_placeholder_1, 51.109459, 17.032681));
        placeHashMap.put("Plac Solny", new Place("Plac Solny", "Rynek pomocniczy Starego Miasta we Wrocławiu, regularny, kwadratowy, wytyczony w 1242 na południowy zachód od Rynku", R.drawable.plac_solny_placeholder, 51.1095624, 17.0294994));
        placeHashMap.put("Uniwersytet Wrocławski", new Place("Uniwersytet Wrocławski", "Jeden z osiemnastu państwowych uniwersytetów klasycznych w Polsce z siedzibą we Wrocławiu", R.drawable.uniwersytet_placeholder, 51.114005, 17.034463));
        placeHashMap.put("Browar Stu Mostów", new Place("Browar Stu Mostów", "Wrocławskie Ferrari piwa craftowego", R.drawable.browar_stu_mostow_placeholder, 51.131794, 17.059257));
        placeHashMap.put("Most Zwierzyniecki", new Place("Most Zwierzyniecki", "Przeprawa mostowa we wschodniej części miasta, nad Starą Odrą, łącząca centrum miasta z osiedlami Dąbie, Biskupin, Sępolno i Bartoszowice", R.drawable.most_zwierzyniecki_placeholder, 51.108197, 17.069728));
        placeHashMap.put("Stara Odra BeachBar", new Place("Stara Odra BeachBar", "Bar, miejsca do gry w siatkówkę i zabawy dla dzieci.", R.drawable.beach_bar_placeholder, 51.133962, 17.039130));
        placeHashMap.put("Hala Stulecia", new Place("Hala Stulecia", "Hala widowiskowo-sportowa znajdująca się we Wrocławiu, na osiedlu Zalesie, w parku Szczytnickim", R.drawable.hala_placeholder, 51.106870, 17.077281));
        placeHashMap.put("Kolejkowo", new Place("Kolejkowo", "Największa w Polsce makieta kolejowa, z jeżdżącymi miniaturowymi pociągami i samochodami", R.drawable.kolejkowo_placeholder, 51.108029, 17.020205));
        placeHashMap.put("Muzeum Archeologiczne", new Place("Muzeum Archeologiczne", "Muzeum Archeologiczne, Oddział Muzeum Miejskiego Wrocławia", R.drawable.muzeum_placeholder, 51.112872, 17.026405));
        placeHashMap.put("Hala Targowa", new Place("Hala Targowa", "Jeden z najbardziej charakterystycznych budynków Wrocławia, stanowi cenny zabytek sztuki inżynierskiej", R.drawable.hala_targowa_placeholder, 51.112575, 17.039795));

        this.tripHashMap = new HashMap<>();
        List<Place> places = new ArrayList<>();
        places.add(placeHashMap.get("Pręgież"));
        Trip trip = new Trip("Wrocławski rynek", "Sympatyczna wycieczka po wrocławskim rynku umożliwiająca zapoznanie się z najbardziej urokliwymi zakamarkami centrum tego pieknego miasta", Trip.CLASSIC_TRIP, 60, R.drawable.wroclaw_placeholder, places, placeHashMap.get("Plac Solny"), placeHashMap.get("Uniwersytet Wrocławski"));
        tripHashMap.put("Wrocławski rynek", trip);

        places = new ArrayList<>();
        places.add(placeHashMap.get("Browar Stu Mostów"));
        places.add(placeHashMap.get("Most Zwierzyniecki"));
        trip = new Trip("Poznając Odrę", "Rowerowa wycieczka, dzieki której poznasz wrocławskie starorzecze Odry - idealna wyprawa na weekend", Trip.BICYCLE_TRIP, 180, R.drawable.stara_odra_placeholder, places, placeHashMap.get("Stara Odra BeachBar"), placeHashMap.get("Hala Stulecia"));
        tripHashMap.put("Poznając Odrę", trip);

        places = new ArrayList<>();
        places.add(placeHashMap.get("Kolejkowo"));
        places.add(placeHashMap.get("Muzeum Archeologiczne"));
        places.add(placeHashMap.get("Hala Targowa"));
        trip = new Trip("W poszukiwaniu krasnali", "Znajdź wszystkie krasnale w centrum Wrocławia. Pilnuj aby żadnego nie przegapić!", Trip.CLASSIC_TRIP, 120, R.drawable.krasnae_placeholder, places, placeHashMap.get("Plac Solny"), placeHashMap.get("Pręgież"));
        tripHashMap.put("W poszukiwaniu krasnali", trip);
    }

    public List<Trip> getAllTrips() {
        return new ArrayList<>(this.tripHashMap.values());
    }
}
