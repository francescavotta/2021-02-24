
/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ResourceBundle;

import org.jgrapht.Graph;

import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	private Graph grafo;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnGiocatoreMigliore"
    private Button btnGiocatoreMigliore; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMatch"
    private ComboBox <Match> cmbMatch; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Match m = cmbMatch.getValue();
    	if(m == null) {
    		return;
    	}else {
    		grafo = model.creaGrafo(m);
    		txtResult.appendText("Grafo creato");
    		txtResult.appendText("Con vertici : " + model.nVertici() +  "\n");
    		txtResult.appendText("Con archi : " + model.nArchi());
    		
    	}
    }

    @FXML
    void doGiocatoreMigliore(ActionEvent event) {    	
    	txtResult.clear();
    	//posso disabilitare il bottone se prima non creo il grafo
    	
    	if(this.model.getGrafo() == null) {
    		txtResult.appendText("Crea prima il grafo");
    		return;
    	}else {
    		txtResult.appendText("Giocatore migliore: \n" + model.getMigliore());
    	}
    }
    
    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	int N;
    	Match m = this.cmbMatch.getValue();
    	
    	if(this.model.getGrafo() == null) {
    		txtResult.appendText("Crea prima il grafo");
    		return;
    	}
    	
    	if(this.model.getMigliore() == null) {
        		txtResult.appendText("Trova prima il giocatore migliore");
        		return;
        }
    	try {
    		N = Integer.parseInt(this.txtN.getText());
    		model.simula(m, N);
    		txtResult.appendText("I goal fatti da squadra " + model.getSquadraBest()+" sono :" + model.getGolBest() + "\n");
    		txtResult.appendText("I goal fatti da squadra " + model.getSquadraWorst()+" sono :" + model.getGolWorst() + "\n");
    		txtResult.appendText("Gli espulsi della squadra 1 sono :" + model.getEspulsiBest() + "\n");
    		txtResult.appendText("Gli espulsi della squadra 2 sono :" + model.getEspulsiWorst());
    	
    	}catch(NumberFormatException nfe) {
    		txtResult.appendText("Inserire un numero intero nel campo N");
    		return;
    	}
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGiocatoreMigliore != null : "fx:id=\"btnGiocatoreMigliore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMatch != null : "fx:id=\"cmbMatch\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	this.cmbMatch.getItems().addAll(model.getTuttiIMatch());
    }
}
