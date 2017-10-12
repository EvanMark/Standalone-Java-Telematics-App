/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Utilities.Tools;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author iceberg
 */
public class GuiTools {

    /**
     * Creates a comboboxmodel with the cities list
     * @return ComboBoxModel
     */ 
    public static ComboBoxModel CreateComboBox() {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        ArrayList<String> cities = Tools.GetCityNameList("https://en.wikipedia.org/wiki/List_of_cities_in_Switzerland");
        for (String city : cities) {
            dcbm.addElement(city);
        }
        return dcbm;

    }
}
