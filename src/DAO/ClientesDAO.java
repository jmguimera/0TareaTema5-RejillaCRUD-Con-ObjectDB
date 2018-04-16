/*
 * ClientesDAO.java
 */
package DAO;

import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import entity.Clientes;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class ClientesDAO {

    private EntityManagerFactory emf;
    private EntityManager em;

    // insertando un registro
    public void guardaCliente(Clientes cliente) throws Exception {

        try {
            iniciaOperacion();

            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } catch (Exception he) {
            // envia el error he si lo hay a la rutina que maneja los mensajes
            manejaExcepcion(he);
            throw he;
        } finally {
            // en cualquier caso cierra la sesion
            em.close();
        }

    }

    // modificando un registro (actualizarlo)
    public void actualizaCliente(Clientes cliente) {
        try {
            iniciaOperacion();
            // actualizacion objectDB
            em.getTransaction().begin();
            cliente.getNombre();
            cliente.getTelefono();
            cliente.getDireccion();
            // confirma la grabacion
            em.getTransaction().commit();
        } catch (Exception he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            em.close();
        }
    }

    // borrando un registro de la tabla
    public void eliminaCliente(Clientes cliente) {
        try {
            iniciaOperacion();
            em.getTransaction().begin();
            em.remove(cliente);
            em.getTransaction().commit();
        } catch (Exception he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            em.close();
        }
    }

    // buscando un registro
    public Clientes getCliente(String clienteNif) throws Exception {
        Clientes cliente = null;
        try {
            iniciaOperacion();
            // localiza el objeto mediante el atributo
            em.getTransaction().begin();

            cliente = this.em.find(Clientes.class, new Clientes(clienteNif, null));

        } finally {
            em.close();
        }

        return cliente;
    }

    // se obtiene una List coleccion con los objetos clientes
    public List<Clientes> getListaClientes() //throws Exception 
    {
        List<Clientes> listaClientes = null;
        TypedQuery<Clientes> query;
        try {
            iniciaOperacion();
            query = em.createQuery("SELECT c FROM Clientes c", Clientes.class);
            listaClientes = query.getResultList();
        } finally {
            em.close();
        }

        return listaClientes;

    }
    // aqui llenamos el modelo de la rejilla    

    public DefaultTableModel llenaRejilla(DefaultTableModel dtm) {
        // rutina de creacion de sesion y transaction

        // creamos objeto lista de una coleccion list tipo Clientes retornado
        // por el metodo list() del objeto q de la clase Query
        List<Clientes> lista = getListaClientes(); 

        // creamos el iterador iter de la lista
        Iterator<Clientes> iter = lista.iterator();

        while (iter.hasNext()) {
            // aqui obtenemos cada objeto cliente que hay en la lista
            Clientes cliente = iter.next();

            // luego creamos el objeto fila con los datos que mostrara la tabla,
            // de los gets de las propiedades que necesitamos nif y nombre
            // que es lo que mostraŕa nuestra rejilla
            Object[] fila = {cliente.getNif(), cliente.getNombre()};

            // añadimos la fila a la rejilla
            dtm.addRow(fila);
        }
        //aqui devolvemos el modelo con el contenido de la rejilla
        return dtm;
    }

    // rutina de inicio de operacion de cada transacción sobre la base de datos
    // crea el objeto sesion e inicia la transaction 
    private void iniciaOperacion() {
        try {
            this.emf = Persistence.createEntityManagerFactory("BaseDatos/clientes.odb");
            this.em = emf.createEntityManager();
        } catch (Exception ex) {
            System.out.println("Error de exception: " + ex.getMessage());
        }
    }

    // rutina de captura de errores de Exception 
    private void manejaExcepcion(Exception he) {
        // cualquier error que suceda devuelve todo a su estado anterior de la
        // transaction en curso en la sesion
        em.getTransaction().rollback();

        try {
            throw new Exception("Ocurrió un error de acceso a datos en ClienteDAO", he);
        } catch (Exception ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
