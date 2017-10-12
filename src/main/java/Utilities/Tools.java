package Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Tools {

    static int counter;
    static long s_time;
    static long e_time;
    static boolean IOflag;

//method of class Tools, which takes as a parameter a string(url) 
    //and returns an arraylist with the cities.
    public static ArrayList<String> GetCityNameList(String url) {
        ArrayList<String> cities = new ArrayList();
        try {
            Document doc;

            doc = Jsoup.connect(url).get();//Using Jsoup the url is aquired and placed into a Document
            Element content = doc.getElementById("mw-content-text");//Parsing the url by extracting data through their id
            Elements table = content.getElementsByClass("wikitable");//and in second step by class
            //Here the table with the required cities is obtained
            //Further parsing to obtain the first column(only the names of cities)
            Elements tr = table.select("tr");//tr is recognized by HTML as each row

            for (int i = 2; i < tr.size(); i++) {//for loop to obtain only the element of each row(mere name)

                String city = tr.get(i).select("td").first().text();//td is recognized by HTML as each element 
                //a way to erase problems or unwanted results from HTML Parser
                city = deAccent(city);//using deAccent method to neutralize all the unreadable parameters of the cities
                city = city.replaceAll("1", "").replaceAll("²", "");//replace with a blank unwanted characters
                cities.add(city);//then add each city into an arrayList collection

            }

        } catch (IOException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cities;

    }

    //this is the method where the normalization happens.This function using Normalizer and Pattern classes convert non enlish character(¨) to blanks
    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    //method which takes a string (url for the API call) and returns a stringbuilder
    public static StringBuilder GetResponse(String url1) {

        StringBuilder response = new StringBuilder();
        try {//Using the API call, we take in a string the response we need(stations/connections)

            URL obj = new URL(url1);//make the call
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");//GET----code word in HTTP protocol to get wanted data
            con.setRequestProperty("User-Agent", "Mozilla/5.0");//Using preferably Mozilla Firefox
            //create a stream through the connection 
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            //until we have no more data to read store the data to a StringBuilder called response 
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();//then close the stream
        } catch (MalformedURLException ex) {
            Logger.getLogger(Tools.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Tools.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.err.println("Don't worry! The execution will be completed normally");
            IOflag = false;
        }
        return response;
    }

//After extracting data from wikipedia and take the info from API 
//Using JSON , parsing the results from API for Locations
    public static JSONObject GetJSONLoc(String city) {
        JSONObject jsonObject = new JSONObject();
        try {//Using GetResponse method replace the blank space with character (%20) , due to HTML's way to express the blank space
            StringBuilder response = new StringBuilder();
            do {//loop for redoing the API call if it fails
                IOflag = true;
                response = Tools.GetResponse("http://transport.opendata.ch/v1/locations?query=" + city.replaceAll(" ", "%20"));
            } while (!IOflag);
            JSONParser parser = new JSONParser();
            JSONObject jsonobject = (JSONObject) parser.parse(response.toString());//parsing response StringBuilder
            JSONArray array = (JSONArray) jsonobject.get("stations");//choose the stations array 
            if (!array.isEmpty()) {
                jsonObject = (JSONObject) array.get(0);//and get the first result,which has the highest score in the quest
            }
        } catch (ParseException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonObject;

    }

    //Using JSON , parsing the results from API for Connections
    //parameters are the two IDs from the locations used in the call
    public static boolean GetJSONCon(int id1, int id2) {
        boolean flag = false;
        JSONArray array = new JSONArray();
        StringBuilder response = new StringBuilder();
        try {//Using GetResponse method with the two IDs added
            do {//loop for redoing the API call if it fails
                IOflag = true;
                response = Tools.GetResponse("http://transport.opendata.ch/v1/connections?from=" + id1 + "&to=" + id2 + "&direct=1");
            } while (!IOflag);
            JSONParser parser = new JSONParser();
            JSONObject jsonobject = (JSONObject) parser.parse(response.toString());//parsing response StringBuilder
            array = (JSONArray) jsonobject.get("connections");//choose the connections array 
            if (!array.isEmpty()) {//if the array is not empty there is a direct link and the method returns true
                flag = true;

            }
        } catch (ParseException ex) {
            Logger.getLogger(Tools.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return flag;//if the array is empty there is not a direct link and the method returns false
    }

    //print the starting menu
    public static void PrintMenu() {
        System.out.println("Choose an option: Type the wanted number ONLY!!! ");
        System.out.println("1.Update/Create new location file");
        System.out.println("2.Read from location file");
        System.out.println("3.Update/Create Location Database using the API");
        System.out.println("4.Update/Create Location Database using file");
        System.out.println("5.Load locations from Database");
        System.out.println("6.Update/Create new connection file");
        System.out.println("7.Read from connection file");
        System.out.println("8.Update/Create Connection Database using file");
        System.out.println("9.Load Connections from Database");
        System.out.println("10.Get non-direct links.!!!!This is a test and not proper for use.Please do not choose");
        System.out.println("11.Quit Application");
    }

}
