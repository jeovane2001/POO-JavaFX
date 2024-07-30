package model;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import com.j256.ormlite.table.TableUtils;
import java.util.List;
import java.util.ArrayList;

public class PacienteRepositorio
{
    private static Database database;
    private static Dao<Paciente, Integer> dao;
    private List<Paciente> loadedPacientes;
    private Paciente loadedPaciente;
    
    public PacienteRepositorio(Database database) {
        PacienteRepositorio.setDatabase(database);
        loadedPacientes = new ArrayList<Paciente>();
    }
    
    public static void setDatabase(Database database) {
        PacienteRepositorio.database = database;
        try {
            dao = DaoManager.createDao(database.getConnection(), Paciente.class);
            TableUtils.createTableIfNotExists(database.getConnection(), Paciente.class);
        }
        catch(SQLException e) {
            System.out.println(e);
        }            
    }
    
    public Paciente create(Paciente paciente) {
        int nrows = 0;
        try {
            nrows = dao.create(paciente);
            if ( nrows == 0 )
                throw new SQLException("Error: object not saved");
            this.loadedPaciente = paciente;
            loadedPacientes.add(paciente);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return paciente;
    }    

    public void update(Paciente Paciente) {
      // TODO
    }

    public void delete(Paciente Paciente) {
      // TODO
    }
    
    public Paciente loadFromId(int id) {
        try {
            this.loadedPaciente = dao.queryForId(id);
            if (this.loadedPaciente != null)
                this.loadedPacientes.add(this.loadedPaciente);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedPaciente;
    }    
    
    public List<Paciente> loadAll() {
        try {
            this.loadedPacientes =  dao.queryForAll();
            if (this.loadedPacientes.size() != 0)
                this.loadedPaciente = this.loadedPacientes.get(0);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedPacientes;
    }

    // getters and setters ommited...        
}