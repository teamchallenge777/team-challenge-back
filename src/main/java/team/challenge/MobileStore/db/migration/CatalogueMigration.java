package team.challenge.MobileStore.db.migration;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.bson.types.ObjectId;
import team.challenge.MobileStore.model.Brand;
import team.challenge.MobileStore.model.Catalogue;
import team.challenge.MobileStore.model.CatalogueGroup;
import team.challenge.MobileStore.model.CatalogueGroupSpecification;
import team.challenge.MobileStore.repositories.BrandRepository;
import team.challenge.MobileStore.repositories.CatalogueRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ChangeLog(order = "101")
public class CatalogueMigration {

    @ChangeSet(order = "101", id = "add_catalogue", author = "Timur")
    public void addCatalogue(CatalogueRepository catalogueRepository, BrandRepository brandRepository) {

        List<Brand> brands = brandRepository.findAll();

        Catalogue SmartphonesAndPhones = new Catalogue();
        SmartphonesAndPhones.setId(new ObjectId().toString());
        SmartphonesAndPhones.setTitle("Smartphones and Phones");
        SmartphonesAndPhones.setGroupSpecifications(new ArrayList<>());

        CatalogueGroup SmartphonesGroup = new CatalogueGroup();
        SmartphonesGroup.setNameOfGroup("Smartphones");
        SmartphonesGroup.setHashtagsOfName(Map.of("catalogue", SmartphonesAndPhones.getId()));
        SmartphonesGroup.setCatalogueGroupSpecifications(new ArrayList<>());

        CatalogueGroupSpecification AppleCatalogueGroupSpecification = new CatalogueGroupSpecification();
        AppleCatalogueGroupSpecification.setTitle("Apple");
        AppleCatalogueGroupSpecification.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Apple")));

        CatalogueGroupSpecification XiaomiGroupSpecification = new CatalogueGroupSpecification();
        XiaomiGroupSpecification.setTitle("Xiaomi");
        XiaomiGroupSpecification.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Xiaomi")));

        CatalogueGroupSpecification SamsungGroupSpecification = new CatalogueGroupSpecification();
        SamsungGroupSpecification.setTitle("Samsung");
        SamsungGroupSpecification.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Samsung")));

        CatalogueGroupSpecification NothingGroupSpecification = new CatalogueGroupSpecification();
        NothingGroupSpecification.setTitle("Nothing");
        NothingGroupSpecification.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Nothing")));

        CatalogueGroupSpecification RealmeGroupSpecification = new CatalogueGroupSpecification();
        RealmeGroupSpecification.setTitle("Realme");
        RealmeGroupSpecification.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Realme")));

        CatalogueGroupSpecification GooglePixelGroupSpecification = new CatalogueGroupSpecification();
        GooglePixelGroupSpecification.setTitle("Google Pixel");
        GooglePixelGroupSpecification.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Google Pixel")));

        CatalogueGroupSpecification NokiaGroupSpecification = new CatalogueGroupSpecification();
        NokiaGroupSpecification.setTitle("Nokia");
        NokiaGroupSpecification.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Nokia")));

        CatalogueGroupSpecification MotorolaGroupSpecification = new CatalogueGroupSpecification();
        MotorolaGroupSpecification.setTitle("Motorola");
        MotorolaGroupSpecification.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Motorola")));

        CatalogueGroupSpecification OnePlusGroupSpecification = new CatalogueGroupSpecification();
        OnePlusGroupSpecification.setTitle("OnePlus");
        OnePlusGroupSpecification.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "OnePlus")));

        CatalogueGroupSpecification RedmiGroupSpecification = new CatalogueGroupSpecification();
        RedmiGroupSpecification.setTitle("Redmi");
        RedmiGroupSpecification.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Redmi")));

        CatalogueGroupSpecification ZTEGroupSpecification = new CatalogueGroupSpecification();
        ZTEGroupSpecification.setTitle("ZTE");
        ZTEGroupSpecification.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "ZTE")));

        CatalogueGroupSpecification OPPOGroupSpecification = new CatalogueGroupSpecification();
        OPPOGroupSpecification.setTitle("OPPO");
        OPPOGroupSpecification.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "OPPO")));

        SmartphonesGroup.getCatalogueGroupSpecifications().addAll(List.of(AppleCatalogueGroupSpecification, XiaomiGroupSpecification, SamsungGroupSpecification, NothingGroupSpecification, RealmeGroupSpecification, GooglePixelGroupSpecification, NokiaGroupSpecification, MotorolaGroupSpecification, OnePlusGroupSpecification, RedmiGroupSpecification, ZTEGroupSpecification, OPPOGroupSpecification));


        CatalogueGroup AppleGroup = new CatalogueGroup();
        AppleGroup.setNameOfGroup("Apple");
        AppleGroup.setHashtagsOfName(Map.of("catalogue", SmartphonesAndPhones.getId()));
        AppleGroup.setCatalogueGroupSpecifications(new ArrayList<>());


        CatalogueGroupSpecification AppleIphone14ProMax = new CatalogueGroupSpecification();
        AppleIphone14ProMax.setTitle("Apple Iphone 14 Pro Max");
        AppleIphone14ProMax.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Apple"), "series", "iphone 14 pro max"));

        CatalogueGroupSpecification AppleIphone14Pro = new CatalogueGroupSpecification();
        AppleIphone14Pro.setTitle("Apple Iphone 14 Pro");
        AppleIphone14Pro.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Apple"), "series", "iphone 14 pro"));

        CatalogueGroupSpecification AppleIphone14 = new CatalogueGroupSpecification();
        AppleIphone14.setTitle("Apple Iphone 14");
        AppleIphone14.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Apple"), "series", "iphone 14"));

        CatalogueGroupSpecification AppleIphone13ProMax = new CatalogueGroupSpecification();
        AppleIphone13ProMax.setTitle("Apple Iphone 13 Pro Max");
        AppleIphone13ProMax.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Apple"), "series", "iphone 13 pro max"));

        CatalogueGroupSpecification AppleIphone13Pro = new CatalogueGroupSpecification();
        AppleIphone13Pro.setTitle("Apple Iphone 13 Pro");
        AppleIphone13Pro.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Apple"), "series", "iphone 13 pro"));

        CatalogueGroupSpecification AppleIphone13 = new CatalogueGroupSpecification();
        AppleIphone13.setTitle("Apple Iphone 13");
        AppleIphone13.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Apple"), "series", "iphone 13"));

        CatalogueGroupSpecification AppleIphone13Mini = new CatalogueGroupSpecification();
        AppleIphone13Mini.setTitle("Apple Iphone 13 mini");
        AppleIphone13Mini.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Apple"), "series", "iphone 13 mini"));

        CatalogueGroupSpecification AppleIphone12ProMax = new CatalogueGroupSpecification();
        AppleIphone12ProMax.setTitle("Apple Iphone 12 Pro Max");
        AppleIphone13ProMax.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Apple"), "series", "iphone 12 pro max"));

        CatalogueGroupSpecification AppleIphone12Pro = new CatalogueGroupSpecification();
        AppleIphone12Pro.setTitle("Apple Iphone 12 Pro");
        AppleIphone12Pro.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Apple"), "series", "iphone 12 pro"));

        CatalogueGroupSpecification AppleIphone12 = new CatalogueGroupSpecification();
        AppleIphone12.setTitle("Apple Iphone 12");
        AppleIphone12.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Apple"), "series", "iphone 12"));

        CatalogueGroupSpecification AppleIphone12Mini = new CatalogueGroupSpecification();
        AppleIphone12Mini.setTitle("Apple Iphone 12 mini");
        AppleIphone12Mini.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Apple"), "series", "iphone 12 mini"));

        AppleGroup.getCatalogueGroupSpecifications().addAll(List.of(AppleIphone14ProMax, AppleIphone14Pro, AppleIphone14, AppleIphone13ProMax, AppleIphone13Pro, AppleIphone13, AppleIphone13Mini, AppleIphone12ProMax, AppleIphone12Pro, AppleIphone12, AppleIphone12Mini));


        CatalogueGroup SamsungGroup = new CatalogueGroup();
        SamsungGroup.setNameOfGroup("Samsung");
        SamsungGroup.setHashtagsOfName(Map.of("catalogue", SmartphonesAndPhones.getId()));
        SamsungGroup.setCatalogueGroupSpecifications(new ArrayList<>());

        CatalogueGroupSpecification SamsungGalaxyS23Ultra = new CatalogueGroupSpecification();
        SamsungGalaxyS23Ultra.setTitle("Samsung Galaxy S23 Ultra");
        SamsungGalaxyS23Ultra.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Samsung"), "series", "samsung galaxy S23 ultra"));

        CatalogueGroupSpecification SamsungGalaxyS23Plus = new CatalogueGroupSpecification();
        SamsungGalaxyS23Plus.setTitle("Samsung Galaxy S23 Plus");
        SamsungGalaxyS23Plus.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Samsung"), "series", "samsung galaxy S23 plus"));

        CatalogueGroupSpecification SamsungGalaxyS23 = new CatalogueGroupSpecification();
        SamsungGalaxyS23.setTitle("Samsung Galaxy S23");
        SamsungGalaxyS23.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Samsung"), "series", "samsung galaxy S23"));

        CatalogueGroupSpecification SamsungGalaxyA53 = new CatalogueGroupSpecification();
        SamsungGalaxyA53.setTitle("Samsung Galaxy A53");
        SamsungGalaxyA53.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Samsung"), "series", "samsung galaxy A53"));

        CatalogueGroupSpecification SamsungGalaxyA13 = new CatalogueGroupSpecification();
        SamsungGalaxyA13.setTitle("Samsung Galaxy A13");
        SamsungGalaxyA13.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Samsung"), "series", "samsung galaxy A13"));

        SamsungGroup.getCatalogueGroupSpecifications().addAll(List.of(SamsungGalaxyS23Ultra, SamsungGalaxyS23Plus, SamsungGalaxyS23, SamsungGalaxyA53, SamsungGalaxyA13));


        CatalogueGroup RedmiGroup = new CatalogueGroup();
        RedmiGroup.setNameOfGroup("Redmi");
        RedmiGroup.setHashtagsOfName(Map.of("catalogue", SmartphonesAndPhones.getId()));
        RedmiGroup.setCatalogueGroupSpecifications(new ArrayList<>());

        CatalogueGroupSpecification RedmiNote12Pro = new CatalogueGroupSpecification();
        RedmiNote12Pro.setTitle("Redmi Note 12 Pro");
        RedmiNote12Pro.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Redmi"), "series", "redmi note 12 pro"));

        CatalogueGroupSpecification RedmiNote12 = new CatalogueGroupSpecification();
        RedmiNote12.setTitle("Redmi Note 12");
        RedmiNote12.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Redmi"), "series", "redmi note 12"));

        CatalogueGroupSpecification RedmiNote11Pro = new CatalogueGroupSpecification();
        RedmiNote11Pro.setTitle("Redmi Note 11 Pro");
        RedmiNote11Pro.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Redmi"), "series", "redmi note 11 pro"));

        CatalogueGroupSpecification RedmiNote10A = new CatalogueGroupSpecification();
        RedmiNote10A.setTitle("Redmi Note 10A");
        RedmiNote10A.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Redmi"), "series", "redmi note 10A"));

        RedmiGroup.getCatalogueGroupSpecifications().addAll(List.of(RedmiNote12Pro, RedmiNote12, RedmiNote11Pro, RedmiNote10A));

        CatalogueGroup XiaomiGroup = new CatalogueGroup();
        XiaomiGroup.setNameOfGroup("Xiaomi");
        XiaomiGroup.setHashtagsOfName(Map.of("catalogue", SmartphonesAndPhones.getId()));
        XiaomiGroup.setCatalogueGroupSpecifications(new ArrayList<>());

        CatalogueGroupSpecification Xiaomi13Lite = new CatalogueGroupSpecification();
        Xiaomi13Lite.setTitle("Xiaomi 13 Lite");
        Xiaomi13Lite.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Xiaomi"), "series", "xiaomi 13 lite"));

        CatalogueGroupSpecification XiaomiPocoX5Pro5G = new CatalogueGroupSpecification();
        XiaomiPocoX5Pro5G.setTitle("Xiaomi Poco X5 Pro 5G");
        XiaomiPocoX5Pro5G.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Xiaomi"), "series", "xiaomi poco X5 pro 5G"));

        CatalogueGroupSpecification XiaomiPocoM5 = new CatalogueGroupSpecification();
        XiaomiPocoM5.setTitle("Xiaomi Poco M5");
        XiaomiPocoM5.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Xiaomi"), "series", "xiaomi poco M5"));

        CatalogueGroupSpecification XiaomiPocoM5s = new CatalogueGroupSpecification();
        XiaomiPocoM5s.setTitle("Xiaomi Poco M5s");
        XiaomiPocoM5s.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Xiaomi"), "series", "xiaomi poco M5s"));

        CatalogueGroupSpecification Xiaomi11LiteNE = new CatalogueGroupSpecification();
        Xiaomi11LiteNE.setTitle("Xiaomi 11 Lite NE");
        Xiaomi11LiteNE.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "brand", findBrandIdByName(brands, "Xiaomi"), "series", "xiaomi 13 lite ne"));

        XiaomiGroup.getCatalogueGroupSpecifications().addAll(List.of(Xiaomi13Lite, XiaomiPocoX5Pro5G, XiaomiPocoM5, XiaomiPocoM5s, Xiaomi11LiteNE));

        CatalogueGroup AccessoriesGroup = new CatalogueGroup();
        AccessoriesGroup.setNameOfGroup("Accessories");
        AccessoriesGroup.setHashtagsOfName(Map.of("catalogue", SmartphonesAndPhones.getId()));
        AccessoriesGroup.setCatalogueGroupSpecifications(new ArrayList<>());

        CatalogueGroupSpecification ScreenProtectorGlasses = new CatalogueGroupSpecification();
        ScreenProtectorGlasses.setTitle("Screen Protector Glasses");
        ScreenProtectorGlasses.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "series", "screen protector glasses"));

        CatalogueGroupSpecification Cases = new CatalogueGroupSpecification();
        Cases.setTitle("Cases");
        Cases.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "series", "cases"));

        CatalogueGroupSpecification Charges = new CatalogueGroupSpecification();
        Charges.setTitle("Cases");
        Charges.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "series", "charges"));

        CatalogueGroupSpecification MemoryCards = new CatalogueGroupSpecification();
        MemoryCards.setTitle("MemoryCards");
        MemoryCards.setHashTagsOfTitle(Map.of("catalogue", SmartphonesAndPhones.getId(), "series", "memory cards"));

        AccessoriesGroup.getCatalogueGroupSpecifications().addAll(List.of(ScreenProtectorGlasses, Cases, Charges, MemoryCards));

        SmartphonesAndPhones.getGroupSpecifications().addAll(List.of(SmartphonesGroup, AppleGroup, RedmiGroup, XiaomiGroup, AccessoriesGroup));

        Catalogue LaptopAndPCs = new Catalogue();
        LaptopAndPCs.setId(new ObjectId().toString());
        LaptopAndPCs.setTitle("Laptop and PCs");


        Catalogue Audio = new Catalogue();
        Audio.setId(new ObjectId().toString());
        Audio.setTitle("Audio");


        Catalogue Accessories = new Catalogue();
        Accessories.setId(new ObjectId().toString());
        Accessories.setTitle("Accessories");


        Catalogue TVsAndMultimedia = new Catalogue();
        TVsAndMultimedia.setId(new ObjectId().toString());
        TVsAndMultimedia.setTitle("TVs and Multimedia");


        Catalogue PhotoAndVideo = new Catalogue();
        PhotoAndVideo.setId(new ObjectId().toString());
        PhotoAndVideo.setTitle("Photo and Video");


        Catalogue SmartWatches = new Catalogue();
        SmartWatches.setId(new ObjectId().toString());
        SmartWatches.setTitle("Smart Watches");


        Catalogue HouseholdAppliances = new Catalogue();
        HouseholdAppliances.setId(new ObjectId().toString());
        HouseholdAppliances.setTitle("Household Appliances");


        Catalogue SpecialOffers = new Catalogue();
        SpecialOffers.setId(new ObjectId().toString());
        SpecialOffers.setTitle("Special Offers");

        catalogueRepository.saveAll(List.of(SmartphonesAndPhones, LaptopAndPCs, Audio, Accessories, TVsAndMultimedia, PhotoAndVideo, SmartWatches, HouseholdAppliances, SpecialOffers));
    }

    private String findBrandIdByName(List<Brand> brands, String name) {
        return brands.stream().filter(nameBrand -> nameBrand.getTitle().equals(name)).findFirst().get().getId();
    }
}