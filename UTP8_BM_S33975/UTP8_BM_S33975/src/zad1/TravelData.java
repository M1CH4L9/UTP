package zad1;

import java.io.*;
import java.text.*;
import java.util.*;

public class TravelData {
    private File dataDir;
    private List<Offer> offersList;

    public TravelData(File dataDir){
        this.dataDir = dataDir;
        this.offersList = new ArrayList<>();

        wczytajOferty();
    }

    private void wczytajOferty(){
        if(dataDir == null || !dataDir.exists()){
            return;
        }

        File[] files = dataDir.listFiles();
        if(files == null){
            return;
        }

        for (int i = 0;i <files.length;i++){
            File file = files[i];
            if(file.isFile()){
                try{
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line = null;
                    while((line = reader.readLine()) != null){
                        processLine(line);
                    }
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void processLine(String line){
        try{
            String[] parts = line.split("\t");
            if(parts.length >=7){
                String locateStr = parts[0];
                String country = parts[1];
                String dateBeginStr = parts[2];
                String dateEndStr = parts[3];
                String place = parts[4];
                String priceStr = parts[5];
                String currency = parts[6];

                String[] locateArr = locateStr.split("_");
                Locale locale = new Locale(locateArr[0], locateArr[1]);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateBegin = sdf.parse(dateBeginStr);
                Date dateEnd = sdf.parse(dateEndStr);

                NumberFormat numberFormat = NumberFormat.getInstance(locale);
                priceStr = priceStr.replace("\u00A0", "").trim();
                Number number = numberFormat.parse(priceStr);
                double price = number.doubleValue();

                Offer offer = new Offer(locale, country, dateBegin, dateEnd, place, price, currency);
                offersList.add(offer);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<String> getOffersDescriptionsList(String loc, String dateFormat){
        List<String> result = new ArrayList<>();

        String[] locateArr = loc.split("_");
        Locale localeTarget = new Locale(locateArr[0], locateArr[1]);
        SimpleDateFormat sdfTarget = new SimpleDateFormat(dateFormat);

        for(int i = 0;i <offersList.size();i++){
            Offer offer = offersList.get(i);
            StringBuilder sb = new StringBuilder();

            String translatedCountry = offer.country;

            Locale[] available = Locale.getAvailableLocales();
            for(int j = 0;j<available.length;j++){
                Locale l = available[j];
                if(l.getDisplayCountry(offer.locale).equals(offer.country)){
                    translatedCountry = l.getDisplayCountry(localeTarget);
                    break;
                }
            }
            sb.append(translatedCountry).append(" ");

            sb.append(sdfTarget.format(offer.dateStart)).append(" ");
            sb.append(sdfTarget.format(offer.dateEnd)).append(" ");

            String translatedPlace = translatePlace(offer.place, localeTarget);
            sb.append(translatedPlace).append(" ");

            NumberFormat numberFormat = NumberFormat.getInstance(localeTarget);

            if(localeTarget.getLanguage().equals("pl")){
                numberFormat.setMinimumFractionDigits(0);
                numberFormat.setMaximumFractionDigits(2);
            }else{
                numberFormat.setMinimumFractionDigits(1);
                numberFormat.setMaximumFractionDigits(2);
            }
            sb.append(numberFormat.format(offer.price)).append(" ");

            sb.append(offer.currency);
            result.add(sb.toString());
        }

        return result;
    }

    public String translatePlace(String word, Locale target) {
        String key = word;

        if (word.equals("jezioro") || word.equals("See")) key = "lake";
        else if (word.equals("morze") || word.equals("Meer")) key = "sea";
        else if (word.equals("góry") || word.equals("Gebirge")) key = "mountains";

        try {
            ResourceBundle bundle = ResourceBundle.getBundle("zad1.bundle", target);
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    public List<Offer> getOffersList(){
        return offersList;
    }

}
