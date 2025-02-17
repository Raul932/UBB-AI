package Threads;

import Domain.BirthdayCake;

import java.util.List;

public class BirthdayCakeUpdaterThread extends Thread {
    private final List<BirthdayCake> cakes;
    private final String newFlavour;

    public BirthdayCakeUpdaterThread(List<BirthdayCake> cakes, String newFlavour) {
        this.cakes = cakes;
        this.newFlavour = newFlavour;
    }

    @Override
    public void run() {
        for (BirthdayCake cake : cakes) {
            // Update cakes with size greater than 8 to a new flavour
            if (cake.getSize() > 8) {
                cake.setFlavour(newFlavour);
                System.out.println("Updated cake " + cake.getId() + " to flavour: " + newFlavour);
            }
        }
    }
}
