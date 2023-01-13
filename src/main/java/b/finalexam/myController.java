/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package b.finalexam;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author ERIK
 */
@RestController 
@CrossOrigin
public class myController {
    
    //deklarasi JPAController ke myController
    Person data = new Person();
    PersonJpaController dctrl = new PersonJpaController();
    
    //membuat Method GET
    @RequestMapping("/GET")
    @ResponseBody
    public List<Person> getDatabyID(){
        List<Person> datas = new ArrayList<>();
        try {datas = dctrl.findPersonEntities();}
        catch(Exception error) {}        
        return datas;
    }
    
    
    //membuat Method POST
    @RequestMapping(value ="/POST", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public String sendData(HttpEntity<String> kiriman) throws Exception{
        String message="no action";
        String json_receive = kiriman.getBody();
        ObjectMapper mapper = new ObjectMapper();
        Person data = new Person(); 
        data = mapper.readValue(json_receive, Person.class);
        dctrl.create(data);
        message ="Data " + data.getNama()+" telah diSimpan";
        return message;
    }
    
    
    //membuat Method PUT
    @RequestMapping(value ="/PUT", method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public String editData(HttpEntity<String> kiriman) throws Exception{
        String message="no action";
        String json_receive = kiriman.getBody();
        ObjectMapper mapper = new ObjectMapper();
        Person newdatas = new Person(); 
        
        newdatas = mapper.readValue(json_receive, Person.class);
        try {dctrl.edit(newdatas);} catch (Exception e){}
        message ="Data " + newdatas.getNama()+" telah diUbah";
        return message;
    }
    
    
    //membuat Method DELETE
    @RequestMapping(value ="/DELETE", method = RequestMethod.DELETE, consumes = APPLICATION_JSON_VALUE)
    public String deleteData(HttpEntity<String> kiriman) throws Exception{
        String message="no action";
        String json_receive = kiriman.getBody();
        ObjectMapper mapper = new ObjectMapper();
        Person newdatas = new Person();     
        newdatas = mapper.readValue(json_receive, Person.class);
        dctrl.destroy(newdatas.getId());
        return  "Data telah Dihapus";
    }
    
}
