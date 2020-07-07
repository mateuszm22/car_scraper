package pages;

import java.util.ArrayList;
import java.util.List;

public class Car {

    public static List<Car> carsData = new ArrayList<>();

    private final String carTitle;
    private final String carId;
    private final String carShortInfo;
    private final String carPrice;
    private final String carCity;
    private final String carLink;

    public Car(String carTitle, String carId, String carShortInfo, String carPrice, String carCity, String carLink) {
        this.carTitle = carTitle;
        this.carId = carId;
        this.carShortInfo = carShortInfo;
        this.carPrice = carPrice;
        this.carCity = carCity;
        this.carLink = carLink;
    }

    public String getCarTitle() {
        return carTitle;
    }

    public String getCarId() {
        return carId;
    }

    public String getCarShortInfo() {
        return carShortInfo;
    }

    public String getCarPrice() {
        return carPrice;
    }

    public String getCarCity() {
        return carCity;
    }

    public String getCarLink() {
        return carLink;
    }
}
