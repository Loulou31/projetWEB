/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.Villageois;

/**
 *
 * @author gaunetc
 */
public class VillageoisDAO extends AbstractDatabaseDAO {

    public VillageoisDAO(DataSource ds) {
        super(ds);
    }

    

    /**
     * @brief Retourne un villageois à partie de son pseudo
     * @param pseudo
     * @return villageois
     */
    public Villageois getVillageois(String pseudo) {
        Villageois villageois;
        try (
              Connection conn = getConn();) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM JOUEUR WHERE login = ?");
            st.setString(1, pseudo);
            ResultSet rs = st.executeQuery();
            System.out.println("RS : "+rs.next());
            villageois = new Villageois(rs.getString("login"),
                    rs.getInt("rolePartie"),
                    rs.getInt("Statut"),
                    rs.getString("Pouvoir"),
                    rs.getInt("IdPartie"));
        } catch (SQLException e) {
            throw new DAOException("Erreur BD getVillageois" + e.getMessage(), e);
        }
        return villageois;
    }

    /**
     * @brief Ajout d'un joueur dans une partie
     * @param pseudo
     * @param idPartie 
     */
    public void addPlayer(String pseudo, int idPartie) {
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("INSERT INTO JOUEUR VALUES  (?, -1, 1, ?, ?) ");
            st.setString(1, pseudo);
            st.setString(2, "rien");
            st.setInt(3, idPartie);
            st.executeUpdate();
            st = conn.prepareStatement("Update Partie set nbJoueursVivants = nbJoueursVivants + 1 Where IdPartie = ?");
            st.setInt(1, idPartie);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * @brief Mise à jour du statut d'un villageois lorsqu'il meurt
     * @param idPartie
     * @param pseudo 
     */
    public void mortVillageois(int idPartie, String pseudo) {
        try (Connection conn = getConn()) {
            /* On passe le statut du villageois à mort = 0 */
            PreparedStatement st = conn.prepareStatement("Update Joueur set Statut = 0 Where login = ?"); 
            st.setString(1, pseudo);
            st.executeUpdate();
            /* On décrémente le nombre de joueurs vivants de la partie */
            st = conn.prepareStatement("Update Partie set nbJoueursVivants = nbJoueursVivants - 1 Where IdPartie = ?");
            st.setInt(1, idPartie);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * @brief Supprime un villageois de la partie
     * @param pseudo 
     */
    public void supprimerVillageois (String pseudo) {
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("DELETE FROM Joueur WHERE login = ?");
            st.setString(1, pseudo);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD supp villageois " + e.getMessage(), e);
        }
    }
    
    /**
     * @brief Supprime tous les villageois de la partie
     * @param idPartie 
     */
    public void supprimerVillageois(int idPartie) {
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("DELETE FROM Joueur WHERE IdPartie = ?");
            st.setInt(1, idPartie);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * @brief Retourne nombre de joueurs dans la partie
     * @param idPartie
     * @return 
     */
    public int nombreJoueursPartie(int idPartie) {
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT count(*) FROM Joueur WHERE IdPartie = ?");
            st.setInt(1, idPartie);
            ResultSet rs = st.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * @brief Mise à jour rôle joueur
     * @param pseudo
     * @param role
     */
    public void updatePlayerRole(String pseudo, int role) {
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("UPDATE JOUEUR SET rolePartie = ?, pouvoir = 'rien' WHERE login = ?");
            st.setInt(1, role);
            st.setString(2, pseudo);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * @brief Mise à jour statut joueur (vivant/mort)
     * @param pseudo
     * @param statut
     */
    public void updatePlayerStatus(String pseudo, int statut) {
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("UPDATE JOUEUR SET Statut = ? WHERE login = ?");
            st.setInt(1, statut);
            st.setString(2, pseudo);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * @brief Mise à jour Pouvoir joueur
     * @param pseudo
     * @param pouvoir 
     */
    public void updatePlayerStatus(String pseudo, String pouvoir) {
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("UPDATE JOUEUR SET Pouvoir = ? WHERE login = ?");
            st.setString(1, pouvoir);
            st.setString(2, pseudo);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    
    
    /**
     * @brief Retourne la liste des villageois de la partie
     * @param idPartie
     * @return liste des villageois
     */
    public List<Villageois> getListVillageois(int idPartie) {
        List<Villageois> result = new ArrayList<Villageois>();
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM JOUEUR WHERE IdPartie = ?");
            st.setInt(1, idPartie);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Villageois villageois
                        = new Villageois(rs.getString("login"),
                                rs.getInt("rolePartie"),
                                rs.getInt("Statut"),
                                rs.getString("Pouvoir"),
                                rs.getInt("IdPartie"));
                result.add(villageois);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD Liste villageois" + e.getMessage(), e);
        }
        return result;
    }

    /**
     * @brief Accède à la liste des villageois vivants
     * @param idPartie
     * @return  liste des villageois vivants
     */
    public List<Villageois> getListVillageoisVivants(int idPartie) {
        List<Villageois> result = new ArrayList<Villageois>();
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM JOUEUR WHERE IdPartie = ? and Statut = 1");
            st.setInt(1, idPartie);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Villageois villageois
                        = new Villageois(rs.getString("login"),
                                rs.getInt("rolePartie"),
                                rs.getInt("Statut"),
                                rs.getString("Pouvoir"),
                                rs.getInt("IdPartie"));
                result.add(villageois);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD Liste villageois" + e.getMessage(), e);
        }
        return result;
    }
    
    /**
     * @brief Accède à la liste des villageois éliminés
     * @param idPartie
     * @return liste des villageois éliminés
     */
    public List<Villageois> getListVillageoisMorts(int idPartie) {
        List<Villageois> result = new ArrayList<Villageois>();
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM JOUEUR WHERE IdPartie = ? and Statut = 0");
            st.setInt(1, idPartie);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Villageois villageois
                        = new Villageois(rs.getString("login"),
                                rs.getInt("rolePartie"),
                                rs.getInt("Statut"),
                                rs.getString("Pouvoir"),
                                rs.getInt("IdPartie"));
                result.add(villageois);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD Liste villageois" + e.getMessage(), e);
        }
        return result;
    }
    

    /**
     *  @brief Accède à la liste des villageois ne possedant pas de pouvoirs
     *
     * @param idPartie
     * @return liste des villageois sans pouvoirs
     */
    public List<Villageois> getListVillageoisSansPouvoir(int idPartie) {
        List<Villageois> result = new ArrayList<Villageois>();
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Joueur WHERE idPartie = ? and pouvoir = ?");
            st.setInt(1, idPartie);
            st.setString(2, "rien");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Villageois humain
                        = new Villageois(rs.getString("login"), rs.getInt("rolePartie"),
                                rs.getInt("Statut"), rs.getString("pouvoir"), rs.getInt("IdPartie"));
                result.add(humain);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * @brief Accède à la liste des humains ne possedant pas de pouvoirs
     *
     * @param idPartie
     * @return liste des villageois sans pouvoirs
     */
    public List<Villageois> getListHumainsSansPouvoir(int idPartie) {
        List<Villageois> result = new ArrayList<Villageois>();
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Joueur WHERE IdPartie = ? "
                    + "and Pouvoir = ? and rolePartie = 0");
            st.setInt(1, idPartie);
            st.setString(2, "rien");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Villageois humain
                        = new Villageois(rs.getString("login"), rs.getInt("rolePartie"),
                                rs.getInt("Statut"), rs.getString("Pouvoir"), rs.getInt("IdPartie"));
                result.add(humain);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * @brief Accède à la liste des joueurs sans rôle attribué pour la partie
     * @param idPartie
     * @return liste des joueurs sans rôle
     */
    public List<Villageois> getListJoueurs(int idPartie) {
        List<Villageois> result = new ArrayList<Villageois>();
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Joueur WHERE IdPartie = ? "
                    + " and rolePartie = -1");
            st.setInt(1, idPartie);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Villageois humain
                        = new Villageois(rs.getString("login"), rs.getInt("rolePartie"),
                                rs.getInt("Statut"), rs.getString("Pouvoir"), rs.getInt("IdPartie"));
                result.add(humain);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * @brief Accède à la liste des loups ne possedant pas de pouvoirs
     *
     * @param idPartie
     * @return liste des loups sans pouvoir
     */
    public List<Villageois> getListLoupsSansPouvoir(int idPartie) {
        List<Villageois> result = new ArrayList<Villageois>();
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Joueur WHERE IdPartie = ? "
                    + "and Pouvoir = ? and rolePartie = 1");
            st.setInt(1, idPartie);
            st.setString(2, "rien");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Villageois humain
                        = new Villageois(rs.getString("login"), rs.getInt("rolePartie"),
                                rs.getInt("Statut"), rs.getString("Pouvoir"), rs.getInt("IdPartie"));
                result.add(humain);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD" + e.getMessage(), e);
        }
        return result;
    }

    /**
     * @brief Accède à la liste des humains éliminés
     *
     * @param idPartie
     * @return liste des humains éliminés
     */
    public List<Villageois> getListHumainsMorts(int idPartie) {
        List<Villageois> result = new ArrayList<Villageois>();
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Joueur WHERE id_partie = ? and rolePartie = 0 and statut = 0");
            st.setInt(1, idPartie);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Villageois humain
                        = new Villageois(rs.getString("login"), rs.getInt("rolePartie"),
                                rs.getInt("Statut"), rs.getString("pouvoir"), rs.getInt("IdPartie"));
                result.add(humain);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * @brief Accéder à la liste des humains dans la partie
     *
     * @param idPartie
     * @return lits des humains vivants
     */
    public List<Villageois> getListHumainsVivants(int idPartie) {
        List<Villageois> result = new ArrayList<Villageois>();
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Joueur WHERE idPartie = ? and rolePartie = 0 and statut = 1");
            st.setInt(1, idPartie);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Villageois humain
                        = new Villageois(rs.getString("login"), rs.getInt("rolePartie"),
                                rs.getInt("Statut"), rs.getString("pouvoir"), rs.getInt("IdPartie"));
                result.add(humain);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * @brief Accéder à la liste des humains d'une partie
     *
     * @param idPartie
     * @return liste des humains
     */
    public List<Villageois> getListHumains(int idPartie) {
        List<Villageois> result = new ArrayList<Villageois>();
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Joueur WHERE IdPartie = ? and rolePartie = 0");
            st.setInt(1, idPartie);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Villageois humain
                        = new Villageois(rs.getString("login"), rs.getInt("rolePartie"),
                                rs.getInt("Statut"), rs.getString("pouvoir"), rs.getInt("IdPartie"));
                result.add(humain);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD" + e.getMessage(), e);
        }
        return result;
    }

    /**
     * @brief Accéder à la liste des loups-garou dans la partie
     *
     * @param idPartie
     * @return liste des loups-garou
     */
    public List<Villageois> getListLoups(int idPartie) {
        List<Villageois> result = new ArrayList<Villageois>();
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Joueur WHERE idPartie = ? and rolePartie = 1");
            st.setInt(1, idPartie);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Villageois loup
                        = new Villageois(rs.getString("login"), rs.getInt("rolePartie"),
                                rs.getInt("Statut"), rs.getString("pouvoir"), rs.getInt("IdPartie"));
                result.add(loup);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * @brief Accéder à la liste des loups-garou vivants
     *
     * @param idPartie
     * @return liste des loups vivants
     */
    public List<Villageois> getListLoupsVivants(int idPartie) {
        List<Villageois> result = new ArrayList<Villageois>();
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Joueur WHERE IdPartie = ? and rolePartie = 1 and statut = 1");
            st.setInt(1, idPartie);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Villageois loup
                        = new Villageois(rs.getString("login"), rs.getInt("rolePartie"),
                                rs.getInt("Statut"), rs.getString("pouvoir"), rs.getInt("IdPartie"));
                result.add(loup);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        return result;
    }

    
    /**
     * @brief Accéder à la liste des loupe-garou éliminés
     *
     * @param idPartie
     * @return liste des loups éliminés
     */
    public List<Villageois> getListLoupsMorts(int idPartie) {
        List<Villageois> result = new ArrayList<Villageois>();
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Joueur WHERE idPartie = ? and rolePartie = 1 and statut = 0");
            st.setInt(1, idPartie);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Villageois loup
                        = new Villageois(rs.getString("login"), rs.getInt("rolePartie"),
                                rs.getInt("Statut"), rs.getString("pouvoir"), rs.getInt("IdPartie"));
                result.add(loup);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        return result;
    }

}
