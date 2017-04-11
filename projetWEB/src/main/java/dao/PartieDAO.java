/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author nicolasl
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.Partie;
import modele.Temps;
import modele.Villageois;

public class PartieDAO extends AbstractDatabaseDAO {

    public PartieDAO(DataSource ds) {
        super(ds);
    }

    /**
     * @brief Retourne la liste des parties de l'application
     * @return liste des parties
     */
    public List<Partie> getListePartiesNonEnCours() {
        List<Partie> result = new ArrayList<Partie>();
        try (
                Connection conn = getConn();
                Statement st = conn.createStatement();) {
            ResultSet rs = st.executeQuery("SELECT * FROM PARTIE");
            Temps temps = new Temps();
            while (rs.next()) {
                if (temps.estApres(rs.getInt("HeureDebut"), temps.getTempsLong())) {
                    Partie partie
                            = new Partie(rs.getInt("IdPartie"),
                                    rs.getInt("NbJoueursMin"),
                                    rs.getInt("NbJoueursMax"),
                                    rs.getInt("DureeJour"),
                                    rs.getInt("DureeNuit"),
                                    rs.getInt("HeureDebut"),
                                    rs.getFloat("ProbaPouvoir"),
                                    rs.getFloat("ProportionLG"),
                                    rs.getInt("discussionSpiritisme"),
                                    rs.getInt("contamination"),
                                    rs.getInt("voyance"));

                    result.add(partie);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        return result;
    }

    
    
    
    /**
     * @brief Passe le pouvoir de contamination à utilisé pour la nuit en cours
     * @param idPartie
     * @param u 
     */
    public void passerContamination(int idPartie, int u) {
        try (
                Connection conn = getConn();) {
            PreparedStatement st = conn.prepareStatement("UPDATE Partie set contamination = ? Where idPartie = ?");
            st.setInt(1, u);
            st.setInt(2, idPartie);
            ResultSet rs = st.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD passer spirit" + e.getMessage(), e);
        }
    }

    /**
     * @brief Passe le pouvoir de voyance à utilisé pour la nuit en cours
     * @param idPartie
     * @param u 
     */
    public void passerVoyance(int idPartie, int u) {
        try (
                Connection conn = getConn();) {
            PreparedStatement st = conn.prepareStatement("UPDATE Partie set voyance = ? Where idPartie = ?");
            st.setInt(1, u);
            st.setInt(2, idPartie);
            ResultSet rs = st.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD passer spirit" + e.getMessage(), e);
        }
    }
    

    /**
     * @brief Passe le pouvoir de spiritisme à utilisé pour la nuit en cours
     * @param idPartie
     * @param u 
     */
    public void passerSpiritisme(int idPartie, int u) {
        try (
                Connection conn = getConn();) {
            PreparedStatement st = conn.prepareStatement("UPDATE Partie set discussionSpiritisme = ? Where idPartie = ?");
            st.setInt(1, u);
            st.setInt(2, idPartie);
            ResultSet rs = st.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD passer spirit" + e.getMessage(), e);
        }
    }

    /**
     * @brief Actualise le paramètre enCours de la partie si elle a commencé ou non
     * @param idPartie
     * @param b 
     */
    public void updateEnCours(int idPartie, boolean b) {
        try (
                Connection conn = getConn();) {
            PreparedStatement st = conn.prepareStatement("UPDATE Partie set enCours = ? Where idPartie = ?");
            st.setBoolean(1, b);
            st.setInt(2, idPartie);
            ResultSet rs = st.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD passer spirit" + e.getMessage(), e);
        }
    }

    /**
     * @brief Ajoute une partie à la base de données
     * @param idPartie
     * @param nbJoueursMin
     * @param nbJoueursMax
     * @param dureeJour
     * @param dureeNuit
     * @param heureDebut
     * @param probaPouvoir
     * @param proportionLG 
     */
    public void ajouterPartie(int idPartie,
            int nbJoueursMin,
            int nbJoueursMax,
            int dureeJour,
            int dureeNuit,
            int heureDebut,
            float probaPouvoir,
            float proportionLG) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement
            ("INSERT INTO PARTIE (IdPartie, NbJoueursMin, NbJoueursMax, DureeJour, DureeNuit, HeureDebut, ProbaPouvoir, ProportionLG, nbJoueursVivants, discussionSpiritisme, contamination, voyance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 0, 0, 0)");) {
            st.setInt(1, idPartie);
            st.setInt(2, nbJoueursMin);
            st.setInt(3, nbJoueursMax);
            st.setInt(4, dureeJour);
            st.setInt(5, dureeNuit);
            st.setInt(6, heureDebut);
            st.setFloat(7, probaPouvoir);
            st.setFloat(8, proportionLG);
            st.setInt(9, 0);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD ajouter Partie" + e.getMessage(), e);
        }
    }


    /**
     * @brief Récupère une partie à partir de son id
     * @param id
     * @return 
     */
    public Partie getPartie(int id) {
        Partie partie;
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM PARTIE WHERE IdPartie = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            rs.next();
            partie = new Partie(rs.getInt("IdPartie"),
                    rs.getInt("NbJoueursMin"),
                    rs.getInt("NbJoueursMax"),
                    rs.getInt("DureeJour"),
                    rs.getInt("DureeNuit"),
                    rs.getInt("HeureDebut"),
                    rs.getFloat("ProbaPouvoir"),
                    rs.getFloat("ProportionLG"), 
                    rs.getInt("discussionSpiritisme"), 
                    rs.getInt("contamination"), 
                    rs.getInt("voyance"));
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        return partie;
    }


    /**
     * @brief Retourne l'id d'une partie associée à un joueur
     * @param login
     * @return l'id de la partie, ou -1 si le joueur n'a pas de partie
     */
    public int getIDPartieJoueur(String login) {
        ResultSet rs;
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT IdPartie FROM Joueur WHERE login = ?");
            st.setString(1, login);
            rs = st.executeQuery();
            if (!(rs.next())) {
                return -1;
            }
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }


    /**
     * @brief Supprime une partie de la base de données
     * @param id 
     */
    public void supprimerPartie(int id) {
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("DELETE FROM Partie WHERE IdPartie = ?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * @brief Retourne le nombre de joueurs d'une partie
     * @param id
     * @return nombre de joueurs
     */
    public int getNbJoueurs(int id) {
        ResultSet rs;
        int nbJoueurs = 0;
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT login FROM Joueur WHERE idPartie = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                nbJoueurs++;
            }
            return nbJoueurs;
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    
    /**
     * @brief Retourne le nombre de villageois vivants pour la partie donnée
     * @param id
     * @return nombre joueurs vivants
     */
    public int getNbJoueursVivants(int id) {
        try (Connection conn = this.getConn()) {
            PreparedStatement st = conn.prepareStatement
                    ("SELECT * FROM PARTIE WHERE IdPartie = "+ id) ; 
            ResultSet rs = st.executeQuery();
            rs.next();
            return rs.getInt("nbJoueursVivants");
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    

    /**
     * @brief retourne un numéro d'identification disponible pour créer une partie
     * @return id
     */
    public int getIdDispo() {
        ResultSet rs;
        try (Connection conn = getConn()) {
            int resultat = 0;
            PreparedStatement st = conn.prepareStatement("SELECT IdPartie FROM Partie");
            rs = st.executeQuery();
            while (rs.next()) {
                int inter = rs.getInt("IdPartie");
                if (inter > resultat) {
                    resultat = inter;
                }
            }
            return (resultat + 1);
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    
    /**
     * @brief Vérifie si une décision est ratifiée sur la place du village
     * @param idPartie
     * @return true si la décision est ratifiée, false sinon
     */
    public Boolean decisionHumainRatifie(int idPartie){
        //A partir de l'id d'une partie: retourne si oui ou non la partie contient une décision ratifiée
        ResultSet rs;
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Decision_Humain WHERE id_partie = ? and ratifie = 1");
            st.setInt(1, idPartie);
            rs = st.executeQuery();
            return rs.next();
             
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * @brief Donne le pseudo du joueur désigné par la décision ratifiée 
     * sur la place du village
     * @param idPartie
     * @return pseudo du joueur désigné
     */
    public String pseudoDecisionHumainRatifie(int idPartie){
        ResultSet rs;
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Decision_Humain WHERE id_partie = ? and ratifie = 1");
            st.setInt(1, idPartie);
            rs = st.executeQuery();
            rs.next() ; 
            return rs.getString("login_joueur_concerne") ; 
             
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * @brief Vérifie si une décision est ratifiée dans le repaire
     * @param idPartie
     * @return true si la décision est ratifiée, false sinon
     */
    public Boolean decisionLoupRatifie(int idPartie){
        //A partir de l'id d'une partie: retourne si oui ou non la partie contient une décision ratifiée
        ResultSet rs;
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Decision_Loup WHERE id_partie = ? and ratifie = 1");
            st.setInt(1, idPartie);
            rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * @brief Donne le pseudo du joueur désigné par la décision ratifiée 
     * sur la place du village
     * @param idPartie
     * @return pseudo du joueur désigné
     */
    public String pseudoDecisionLoupRatifie(int idPartie) {
        ResultSet rs;
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Decision_Loup WHERE id_partie = ? and ratifie = 1");
            st.setInt(1, idPartie);
            rs = st.executeQuery();
            rs.next();
            return rs.getString("login_joueur_concerne");

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
}
