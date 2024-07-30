package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

import model.PacienteRepositorio;
import view.AppView;

public class AppController implements Initializable {
    @FXML
    private TableView<view.Paciente> tabela;
    @FXML
    private TableColumn<view.Paciente, Integer> idCol;
    @FXML
    private TableColumn<view.Paciente, String> nomeCompletoCol;
    @FXML
    private TableColumn<view.Paciente, String> dataDeNascimentoCol;
    @FXML
    private TableColumn<view.Paciente, Integer> matriculaCol;
    @FXML
    private TextField idField;
    @FXML
    private TextField nomeCompletoField;
    @FXML
    private TextField dataDeNascimentoField;
    @FXML
    private TextField idPacienteField;
    @FXML
    private Button adicionarButton;
    @FXML
    private Button atualizarButton;
    @FXML
    private Button deletarButton;    
    @FXML
    private Button cancelarButton;    
    @FXML
    private Button salvarButton;
    
    AppView appView;
    
    private static model.Database database = new model.Database("app.sqlite");
    private static model.PacienteRepositorio pacienteRepo = 
        new model.PacienteRepositorio(database);
        
    public AppController() {
        this.appView = new AppView();
    }
    
    public static void main(String[] args) throws Exception {
        AppController appController = new AppController();
        appController.appView.run(args);
    }
    
    private void desabilitarBotoes(boolean adicionar, boolean atualizar, boolean deletar, boolean cancelar, boolean salvar) {
        adicionarButton.setDisable(adicionar);
        atualizarButton.setDisable(atualizar);
        deletarButton.setDisable(deletar);
        cancelarButton.setDisable(cancelar);
        salvarButton.setDisable(salvar);        
    }
    
    private void desabilitarCampos(boolean desabilitado) {
        nomeCompletoField.setDisable(desabilitado);
        idPacienteField.setDisable(desabilitado);
        dataDeNascimentoField.setDisable(desabilitado);
    }
    
    private void limparCampos() {
        idField.setText("");
        dataDeNascimentoField.setText("");
        nomeCompletoField.setText("");
        idPacienteField.setText("");        
    }
    
    @FXML
    public void onCancelarButtonAction() {
        desabilitarCampos(true);
        desabilitarBotoes(false,true,true,true,true);
        limparCampos();
        tabela.getSelectionModel().select(-1);        
    }
    
    @FXML
    public void onSalvarButtonAction() {
        try {
            model.Paciente paciente = new model.Paciente();            
            paciente.setMatricula(Integer.parseInt(idPacienteField.getText()));
            paciente.setNomeCompleto(nomeCompletoField.getText());
            paciente.setDataDeNascimento(dataDeNascimentoField.getText());            
            model.Paciente paciente_salvo = pacienteRepo.create(paciente); 
            view.Paciente pacienteView = modelToView(paciente_salvo);
            tabela.getItems().add(pacienteView);
            tabela.getSelectionModel().select(pacienteView);    
            desabilitarCampos(true);
            desabilitarBotoes(false,true,true,true,true);            
        }
        catch(Exception e) {
            new Alert(AlertType.ERROR, "Erro ao salvar: "+e.getMessage()).show();
        }
    }    
    
    @FXML
    public void onAdicionarButtonAction() {
        tabela.getSelectionModel().select(-1);
        desabilitarCampos(false);
        desabilitarBotoes(true,true,true,false,false);
        limparCampos();
    }

    @FXML
    private void handlePacienteSelected(view.Paciente newSelection) {
        if (newSelection != null)
            idField.setText(Integer.toString(newSelection.getId()));
            dataDeNascimentoField.setText(newSelection.getDataDeNascimento());
            nomeCompletoField.setText(newSelection.getNomeCompleto());
            idPacienteField.setText(Integer.toString(newSelection.getMatricula()));
            desabilitarBotoes(false,false,false,true,true);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        nomeCompletoCol.setCellValueFactory(
                new PropertyValueFactory<>("nomeCompleto"));
        dataDeNascimentoCol.setCellValueFactory(
                new PropertyValueFactory<>("dataDeNascimento"));
        matriculaCol.setCellValueFactory(
                new PropertyValueFactory<>("matricula"));
        tabela.setItems(loadAllEstudantes());
        tabela.getSelectionModel().selectedItemProperty().addListener(
            (observableValue, oldSelection, newSelection) -> {
                handlePacienteSelected(newSelection);
            });
    }
    
    private view.Paciente modelToView(model.Paciente paciente) {
        return new view.Paciente(
            paciente.getId(),
            paciente.getNomeCompleto(),
            paciente.getDataDeNascimento(),
            paciente.getidPaciente()
        );
    }
    
    private ObservableList<view.Paciente> loadAllEstudantes() {
        ObservableList<view.Paciente> lista = 
            FXCollections.observableArrayList();
        List<model.Paciente> listaFromDatabase = pacienteRepo.loadAll();
        for(model.Paciente estudante: listaFromDatabase) {
            lista.add(modelToView(estudante));
        }
        return lista;
    }
}