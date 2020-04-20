package sovellus;

/**
 * Yearly saving's showing graphic control panel, made as one of my University
 * projects
 */
import javafx.application.Application;
import javafx.beans.value.ObservableValue; 
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SaastolaskuriSovellus extends Application {

    public static void main(String[] args) {
        launch(SaastolaskuriSovellus.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane ankkuri = new BorderPane();
        VBox valikko = new VBox();
        BorderPane ylempi = new BorderPane();
        BorderPane alempi = new BorderPane();

        Label kuukausi = new Label("Kuukausittainen tallennus");
        Label korko = new Label("Vuosittainen korko");

        Slider kuukausiTallennus = new Slider(25, 250, 25);
        Slider vuosikorko = new Slider(0, 10, 3);

        Label tamanHetkinenkuukausiTallennus = new Label(kuukausiTallennus.getValue() + "");
        Label tamanHetkinenVuosikorko = new Label(vuosikorko.getValue() + "");
        
        kuukausiTallennus.setMajorTickUnit(25);
        kuukausiTallennus.setSnapToTicks(true);
        
        vuosikorko.setMajorTickUnit(1);
        vuosikorko.setSnapToTicks(true);
        
        kuukausiTallennus.setShowTickLabels(true);
        kuukausiTallennus.setShowTickMarks(true);
        vuosikorko.setShowTickLabels(true);
        vuosikorko.setShowTickMarks(true);

        Toimintalogiikka toiminta = new Toimintalogiikka(kuukausiTallennus.getValue(), vuosikorko.getValue());

        ylempi.setLeft(kuukausi);
        ylempi.setCenter(kuukausiTallennus);
        ylempi.setRight(tamanHetkinenkuukausiTallennus);
        alempi.setLeft(korko);
        alempi.setCenter(vuosikorko);
        alempi.setRight(tamanHetkinenVuosikorko);

        ylempi.setPadding(new Insets(20, 10, 10, 20));
        alempi.setPadding(new Insets(20, 10, 10, 20));

        valikko.getChildren().addAll(ylempi, alempi);

        ankkuri.setTop(valikko);
        ankkuri.setCenter(paneeli(toiminta));

        vuosikorko.valueProperty().addListener(event -> {
            tamanHetkinenVuosikorko.setText((double)Math.round(vuosikorko.getValue() * 100) / 100 + "");
            toiminta.setKuukausiTallennus(kuukausiTallennus.getValue());
            toiminta.setKorko(vuosikorko.getValue());
            ankkuri.setCenter(paneeli(toiminta));
        });

        kuukausiTallennus.valueProperty().addListener(event -> { 
            tamanHetkinenkuukausiTallennus.setText((double)Math.round(kuukausiTallennus.getValue()) + "");
            toiminta.setKuukausiTallennus(kuukausiTallennus.getValue());
            toiminta.setKorko(vuosikorko.getValue());
            ankkuri.setCenter(paneeli(toiminta));
        });

        primaryStage.setScene(new Scene(ankkuri));
        primaryStage.show();
    }

    private LineChart<Number, Number> paneeli(Toimintalogiikka toiminta) {
        NumberAxis yAkseli = new NumberAxis();
        NumberAxis xAkseli = new NumberAxis(0, 30, 1);
        LineChart<Number, Number> graafinenpaneeli = new LineChart(xAkseli, yAkseli);
        graafinenpaneeli.setTitle("Säästölaskuri");
        XYChart.Series vuosissa = new XYChart.Series();
        XYChart.Series vuosissaIlmanKorkoa = new XYChart.Series();
        vuosissa.setName("Korkojen kanssa");
        vuosissaIlmanKorkoa.setName("Ilman korkoa");

        for (int i = 0; i <= 30; i++) {
            vuosissaIlmanKorkoa.getData().add(new XYChart.Data(i, toiminta.getSaldoIlmanKorkoa()));
            vuosissa.getData().add(new XYChart.Data(i, toiminta.getVuodenKorko()));
            toiminta.eteneVuosi();
        }
        graafinenpaneeli.getData().add(vuosissaIlmanKorkoa);
        graafinenpaneeli.getData().add(vuosissa);
        return graafinenpaneeli;
    }
}
