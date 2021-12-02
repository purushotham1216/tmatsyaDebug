package com.org.nic.ts.model.fish_seed_stocking;


public class FishSeedStockingEntryBean1 {

    private String
            no_of_fingerlings_per_kg, total_no_of_kgs, total_no_of_fingerlings,
            entered_species_count,species_name, vehicle_number;

    public FishSeedStockingEntryBean1() {
    }

    public FishSeedStockingEntryBean1(String no_of_fingerlings_per_kg, String total_no_of_kgs, String total_no_of_fingerlings,
                                      String entered_species_count, String species_name, String vehicle_number) {
        this.no_of_fingerlings_per_kg = no_of_fingerlings_per_kg;
        this.total_no_of_kgs = total_no_of_kgs;
        this.total_no_of_fingerlings = total_no_of_fingerlings;
        this.entered_species_count = entered_species_count;
        this.species_name = species_name;
        this.vehicle_number = vehicle_number;
    }

    public String getNo_of_fingerlings_per_kg() {
        return no_of_fingerlings_per_kg;
    }

    public void setNo_of_fingerlings_per_kg(String no_of_fingerlings_per_kg) {
        this.no_of_fingerlings_per_kg = no_of_fingerlings_per_kg;
    }

    public String getTotal_no_of_kgs() {
        return total_no_of_kgs;
    }

    public void setTotal_no_of_kgs(String total_no_of_kgs) {
        this.total_no_of_kgs = total_no_of_kgs;
    }

    public String getTotal_no_of_fingerlings() {
        return total_no_of_fingerlings;
    }

    public void setTotal_no_of_fingerlings(String total_no_of_fingerlings) {
        this.total_no_of_fingerlings = total_no_of_fingerlings;
    }

    public String getEntered_species_count() {
        return entered_species_count;
    }

    public void setEntered_species_count(String entered_species_count) {
        this.entered_species_count = entered_species_count;
    }

    public String getSpecies_name() {
        return species_name;
    }

    public void setSpecies_name(String species_name) {
        this.species_name = species_name;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }
}
