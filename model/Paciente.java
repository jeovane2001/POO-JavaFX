package model;

import java.util.Date;
import java.text.SimpleDateFormat;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;

@DatabaseTable(tableName="estudante")
public class Paciente {
    
    @DatabaseField(generatedId = true)
    private int id;
        
    @DatabaseField(dataType=DataType.STRING)    
    private String nomeCompleto;
    
    @DatabaseField(dataType=DataType.STRING)        
    private String dataDeNascimento;
    
    @DatabaseField(dataType=DataType.INTEGER)        
    private int idPaciente;
    

//Start GetterSetterExtension Source Code

    /**método GET do ID de propriedade */
    public int getId(){
        return this.id;
    }//end method getId

    /**Método atribuiu ID da propriedade*/
    public void setId(int id){
        this.id = id;
    }//end method setId

    /**GET metodo ver nome completo do proprietario*/
    public String getNomeCompleto(){
        return this.nomeCompleto;
    }//end method getNomeCompleto

    /**SET metodo para atribuir nome completo*/
    public void setNomeCompleto(String nomeCompleto){
        this.nomeCompleto = nomeCompleto;
    }//end method setNomeCompleto

    /**GET Method Propertie dataDeNascimento*/
    public String getDataDeNascimento(){
        return this.dataDeNascimento;
    }//end method getDataDeNascimento

    /**SET Method Propertie dataDeNascimento*/
    public void setDataDeNascimento(String dataDeNascimento){
        this.dataDeNascimento = dataDeNascimento;
    }//end method setDataDeNascimento

    /**GET mostra a identificação do paciente*/
    public int getidPaciente(){
        return this.idPaciente;
    }//end method getMatricula

    /**SET id Paciente*/
    public void setMatricula(int idPaciente){
        this.idPaciente = idPaciente;
    }//end method setMatricula

//End GetterSetterExtension Source Code


}//End class