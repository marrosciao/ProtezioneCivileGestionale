package Controller.Compiti;

import Model.GestioneModel;
import Model.Persona;
import Model.Volontario;
import View.BasicFrameView;
import View.ListaggiView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Classe che rappresenta il compito del referente informatico
 */
public class Referenteinformatico {

    private BasicFrameView basicframe;
    private ListaggiView view;
    private GestioneModel model;
    private JComboBox Box;
    private String appoggio;

    private ArrayList<Volontario> UTENTI;


    /*COSTRUTTORI*/

    /*costruttore vuoto*/
    public Referenteinformatico() {

        return;

    }

    public Referenteinformatico(BasicFrameView frame) {

        basicframe = frame;

        view = new ListaggiView();
        view.VisibilitaResettaPasswordButton(true);
        view.setLabel("Lista utenti");


        Box = view.getBox1();

        appoggio = " ";

        model = new GestioneModel(appoggio);

        if(model.SearchSQL())
            //è GIUSTO MA POTEVO RISOLVERLO DIVERSAMENTE
            UTENTI = model.getListutenti();

        stampalista();

        basicframe.setdestra(view.getIntermedio0());

        Listener();

    }

    /**
     * Permette di stampare la lista degli utenti nella Combobox
     */

    public void stampalista () {

        for(Volontario utente : UTENTI)
            Box.addItem(utente.getCognome() + "    -    " +utente.getNome());

    }


    /**
     * Ascolto le azioni dell utente.
     * Botton: ResettaPassword
     */
    private void Listener() {

        JButton resettaPasswordButton =  view.getResettaPasswordButton();
        resettaPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ResettaAction();

            }
        });

    }

    /**
     * Avvia l update(dell utente selezionato) per effettuare l aggiornamento della password.
     * Richiede al referente informatico l immissione dell nuova password
     */
    private void ResettaAction(){

        int Indice;
        Indice = Box.getSelectedIndex();
        String[] appoggio = new String[3];


        if(basicframe.OpotionalMessage("Reset password per "+UTENTI.get(Indice).getNome()+" ?") == 0) {

            String Nuovapass = basicframe.InputMessage("Inserire una nuova password: ");
            //null per l annulla
            if(Nuovapass != null && Nuovapass.length() != 0) {

                appoggio[0] = "pass";
                appoggio[1] = "pass";
                appoggio[2] = Nuovapass;

                //AVREI UTILIZZATO IL POLIMORFISMO
                if (UTENTI.get(Indice).UpdateSQL(appoggio)) {

                    basicframe.Message("Reset avvenuto con successo!");
                    UTENTI.remove(Indice);
                    Box.removeItemAt(Indice);

                }
            }
        }
    }

    @Override
    public String toString() {
        return "Referenteinformatico{" +
                "basicframe=" + basicframe +
                ", view=" + view +
                ", model=" + model +
                ", Box=" + Box +
                ", appoggio='" + appoggio + '\'' +
                ", UTENTI=" + UTENTI +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Referenteinformatico that = (Referenteinformatico) o;

        return view != null ? view.equals(that.view) : that.view == null;
    }

}
