package com.securelogin.secure.Stip;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.*;
import java.sql.*;

@SpringBootApplication
@RestController
public class StripController {

    public static void main(String[] args) {
        SpringApplication.run(StripController.class, args);
    }

    @RequestMapping(value = "/strip", method = RequestMethod.GET)
    public Stripper allCustomers(@RequestHeader("Authorization") String auth) {

        String userPass = auth.substring(6);
        byte[] decA = Base64.decodeBase64(userPass);
        String decS = new String(decA);
        int colon = decS.indexOf(":");
        String aun = decS.substring(0, colon);
        String ap = decS.substring(colon + 1);

        String SQL = String.format("select * from users where username='%s'", aun);
        
        
        Strip userinfo = new Strip();
        Stripper output = new Stripper();

        try {

            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/secureloginexer", "username", "a,plain3");
            Statement stmt = con.createStatement();
            ResultSet records = stmt.executeQuery(SQL);
            while(records.next()){
                userinfo = new Strip();
                userinfo.username = records.getString("username");
                userinfo.password = records.getString("password");
                userinfo.role = records.getString("role");
            }
            con.close();

            if(ap.equals(userinfo.password)){
                output.username = userinfo.username;
                output.role = userinfo.role;
            }
            else{
                output.username = aun;
                output.role = "forbidden";
            }

        } catch (ClassNotFoundException e) {
            output.username = "Class not found Error";
            output.role = e.getMessage();

        } catch (SQLException e) {
            output.username = "SQL Error";
            output.role = e.getMessage();
        }

        return output;
        

    }

}
