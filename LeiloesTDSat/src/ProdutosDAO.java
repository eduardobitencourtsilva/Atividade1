/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        
        
        try {
            conn = new conectaDAO().connectDB();
                        
            prep = conn.prepareStatement("insert into produtos (nome, valor, status) values (?, ?, ?)");
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            
            prep.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){        
        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement("select * from produtos");
            resultset = prep.executeQuery();
            
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                
                produto.setId(resultset.getInt(1));
                produto.setNome(resultset.getString(2));
                produto.setValor(resultset.getInt(3));
                produto.setStatus(resultset.getString(4));

                listagem.add(produto);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listagem;
    }   
    
    public void venderProduto(int id) {
        ArrayList<ProdutosDTO> produtos = listarProdutos();
        
        ProdutosDTO produto = null;
        
        for (ProdutosDTO p : produtos) {
            if (p.getId() == id)
                produto = p;
        }
        
        if (produto != null) {
            
            try {
                conn = new conectaDAO().connectDB();
                prep = conn.prepareStatement("update produtos set status='Vendido' where id=?");
                prep.setInt(1, id);
                prep.executeUpdate();
                
                produto.setStatus("vendido");

                JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
            }
            catch (SQLException ex) {
                Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto. Não há produto de id " + id + ".");
        }
    }
}