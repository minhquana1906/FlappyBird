package DataAccessObject;

import Main.Database;
import Model.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAO_Player implements DAO_Interface<Player> {

    @Override
    public int insert(Player obj) {
        int changes = 0;
        Connection con = null;
        Statement st = null;

        try{
            con = Database.mycon();
            st = con.createStatement();

            String query = "INSERT INTO score_manager (player_name, score) VALUES (" + "'" + obj.getName() + "', " + obj.getScore() + ")";
            changes =  st.executeUpdate(query);
            System.out.println("Recent query: "+query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
        }
        return changes;
    }

    @Override
    public int update(Player obj) {
        int changes = 0;
        Player model = null;
        Connection con = null;
        Statement st = null;

        try{
            con = Database.mycon();
            st = con.createStatement();
            String query = "UPDATE score_manager SET score = " + obj.getScore() + " WHERE player_name = '" + obj.getName() + "'";
            System.out.println("Recent query: "+query);
            changes =  st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return changes;
    }

    @Override
    public int delete(Player obj) {
        return 0;
    }

    @Override
    public ArrayList<Player> selectAll() {
        ArrayList<Player> topPlayers = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet res = null;

        try{
            con = Database.mycon();
            st = con.createStatement();
            String query = "SELECT * FROM score_manager ORDER BY score DESC LIMIT 3";

            System.out.println("Recent query: "+query);
            res = st.executeQuery(query);
            while(res.next()){
                String name = res.getString("player_name");
                int score = res.getInt("score");
                topPlayers.add(new Player(name, score));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try{
                if(res != null){
                    res.close();
                }
                if(st != null){
                    st.close();
                }
                if(con != null){
                    con.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return topPlayers;
    }

    @Override
    public Player selectTopPLayer() {
        Player model = null;
        Connection con = null;
        Statement st = null;
        ResultSet res = null;

        try{
            con = Database.mycon();
            st = con.createStatement();
            String query = "SELECT * FROM score_manager ORDER BY score DESC LIMIT 1";

            System.out.println("Recent query: "+query);
            res = st.executeQuery(query);
            while(res.next()){
                String name = res.getString("player_name");
                int score = res.getInt("score");
                model = new Player(name, score);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try{
                if(res != null){
                    res.close();
                }
                if(st != null){
                    st.close();
                }
                if(con != null){
                    con.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return model;
    }

    @Override
    public ArrayList<Player> selectByCondition(String condition) {
        return null;
    }

    @Override
    public Player selectByName(Player obj) {
        Player model = null;
        Connection con = null;
        Statement st = null;
        try{
            con = Database.mycon();
            st = con.createStatement();

            String query = "SELECT * FROM score_manager WHERE player_name = '" + obj.getName() + "'";

            System.out.println("Recent query: "+query);
            ResultSet res = st.executeQuery(query);
            while(res.next()){
                String name = res.getString("player_name");
                int score = res.getInt("score");
                model = new Player(name, score);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try{
                if(st != null){
                    st.close();
                }
                if (con != null){
                    con.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return model;
    }
}
