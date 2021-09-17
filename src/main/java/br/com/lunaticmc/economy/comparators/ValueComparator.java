package br.com.lunaticmc.economy.comparators;

import br.com.lunaticmc.economy.object.EcoPlayer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class ValueComparator implements Comparator<String> {

    Map<String, EcoPlayer> base;

    public ValueComparator(HashMap<String, EcoPlayer> map) {
        this.base = map;
    }

    public int compare(String a, String b) {
        if (base.get(a).getBalance() >= base.get(b).getBalance()) return -1;
        else return 1;
    }

}