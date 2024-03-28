package com.example.security.controller;

import com.example.security.models.*;
import com.example.security.repositories.*;
import jakarta.persistence.OneToMany;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.security.Permission;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private userRepositorie ur;

    @Autowired
    private rolesRepositorie rlr;

    @Autowired
    private permissionRepositorie pr;

    @Autowired
    private perfilesItemsRepositorie pri;

    @Autowired
    private PermissionAsignadosRepositorie par;

    @Autowired
    private recursosRepositorie rp;

    @Autowired
    private perfilItemAsignadoRepositorie pia;

    @Autowired
    private RecursosAsignadosRepositorie rar;

    @Autowired
    private PerfilesRepositorie per;

    //--------------------------GET-----------------------------------

    //obtener los perfiles del usuario
    @GetMapping("/PerfilesUser")
    public HashMap<String, Object> getPerfiles(Authentication authentication) {
        Optional<user> usuario = this.ur.findByUsername(authentication.getName());
        HashMap<String, Object> response = new HashMap<>();
        List<HashMap<String, Object>> perfilesPer = new ArrayList<>() {
            @Override
            public int indexOf(Object o) {
                int pos = 0;
                if (this.size() < 1) return -1;
                for (HashMap<String, Object> dat : this) {
                    if (dat.get("label").equals((String) o)) return pos;
                    pos++;
                }
                return -1;
            }
        };

        if (usuario.isPresent()) {
            List<String[]> perfiles = this.pri.PerfilesRecurosUsuario(usuario.get().getRoles().getIdRol());
            for (String[] perfile : perfiles) {
                int Position = perfilesPer.indexOf(perfile[0]);
                if (Position == -1) {
                    HashMap<String, Object> Pefil = new HashMap<>();
                    Pefil.put("label", perfile[0]);

                    ArrayList<Map<String, Object>> ListaPerfilItems = new ArrayList<>();
                    Map<String, Object> PerfilItems = new HashMap<>();
                    PerfilItems.put("label", perfile[1]);
                    PerfilItems.put("icon", perfile[2]);
                    PerfilItems.put("href", perfile[3]);
                    PerfilItems.put("id", perfile[4]);
                    ListaPerfilItems.add(PerfilItems);

                    Pefil.put("items", ListaPerfilItems);
                    perfilesPer.add(Pefil);
                } else {

                    ArrayList<Map<String, Object>> ListaPerfilItems = (ArrayList<Map<String, Object>>) perfilesPer.get(Position).get("items");
                    Map<String, Object> PerfilItems = new HashMap<>();
                    PerfilItems.put("label", perfile[1]);
                    PerfilItems.put("icon", perfile[2]);
                    PerfilItems.put("href", perfile[3]);
                    PerfilItems.put("id", perfile[4]);
                    ListaPerfilItems.add(PerfilItems);

                    perfilesPer.get(Position).put("items", ListaPerfilItems);
                }
            }
            response.put("user", perfilesPer);
            response.put("message", "perfiles");
            response.put("successful", true);
        } else {
            response.put("message", "no tiene perfiles");
            response.put("successful", false);
        }

        return response;
    }

    //obtener lo permisos del perfil
    @GetMapping("/PermisosPerfilItem/{idPerfil}/rol/{idRol}")
    public HashMap<String, Object> getPermisoPerfilesItems(@PathVariable int idPerfil, @PathVariable int idRol) {
        ArrayList<String[]> dat = this.rp.findByperfilItem(idPerfil, idRol);
        HashMap<String, Object> response = new HashMap<>();
        if (!dat.isEmpty()) {
            response.put("user", dat);
            response.put("message", "perfiles");
            response.put("successful", true);
        } else {
            response.put("user", null);
            response.put("message", "no hay permisos");
            response.put("successful", false);
        }
        return response;
    }

    //obtener usuario
    @GetMapping
    public HashMap getUser(Authentication auth) {
        HashMap<String, Object> response = new HashMap<String, Object>();

        List<Object[]> usr = this.ur.findUserByUsername(auth.getName());
        if (!usr.isEmpty()) {
            response.put("user", usr);
            response.put("message", "usuario");
            response.put("successful", true);
        } else {
            response.put("user", null);
            response.put("message", "el usuario no existe");
            response.put("successful", false);
        }
        return response;
    }

    //obtener todos los permisos
    @GetMapping("/getAllPermission")
    public HashMap<String, Object> getAllPermission() {
        HashMap<String, Object> response = new HashMap<>();

        List<permission> permisos = this.pr.findAllPermission();

        // Inicializar las relaciones antes de devolver las entidades
        for (permission permiso : permisos) {
            Hibernate.initialize(permiso.getRecurso()); // Accede a la relación para inicializarla
            // Si hay más relaciones, accede a ellas también
        }

        if (!permisos.isEmpty()) {
            List<Map<String, Object>> permisosDTO = permisos.stream()
                    .map(permiso -> {
                        Map<String, Object> permisoDTO = new HashMap<>();
                        permisoDTO.put("idPermission", permiso.getIdPermission());
                        permisoDTO.put("url", permiso.getUrl());
                        permisoDTO.put("method", permiso.getMethod());
                        permisoDTO.put("pertRecurso", permiso.getPertRecurso());
                        permisoDTO.put("permisoRequerido", permiso.getPermisoRequerido());
                        permisoDTO.put("grupoPermiso", permiso.getGrupoPermiso());
                        permisoDTO.put("urlIgnore", permiso.getUrlIgnore());
                        permisoDTO.put("nombre", permiso.getNombre());
                        permisoDTO.put("recursoId", permiso.getRecurso() != null ? permiso.getRecurso().getIdRecurso() : null);
                        permisoDTO.put("recursoNombre", permiso.getRecurso() != null ? permiso.getRecurso().getNameFront() : null);
                        return permisoDTO;
                    })
                    .collect(Collectors.toList());

            response.put("data", permisosDTO);
            response.put("message", "Permisos cargados");
            response.put("successful", true);
        } else {
            response.put("message", "No hay permisos");
            response.put("successful", false);
        }
        return response;
    }

    //obtener los roles
    @GetMapping("/getRoles")
    public HashMap<String, Object> getRoles() {
        List<roles> rolesAll = this.rlr.findAll();
        HashMap<String, Object> response = new HashMap<>();
        response.put("roles", rolesAll);
        response.put("message", "Roles cargados");
        response.put("successful", true);
        return response;
    }

    //obtener los perfiles del rol
    @Transactional
    @GetMapping("/getPerfilesRol/{idRol}")
    public HashMap<String, Object> getPerfilesRol(@PathVariable String idRol) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Optional<roles> rol = this.rlr.findById(idRol);
            if (rol.isPresent()) {
                List<String[]> pefilesItemsRol = this.pri.PerfilesItemsByRol(idRol);
                response.put("perfilesRol", pefilesItemsRol);
                response.put("successful", true);
            } else {
                response.put("permisoRol", null);
                response.put("message", "Permisos cargados");
                response.put("successful", false);
            }
            return response;
        } catch (Exception e) {
            response.put("message", "error capturado: " + e.getMessage());
            response.put("successful", false);
        }
        return response;
    }

    //obtener recursos faltantes de un rol
    @GetMapping("/getRecursosFaltantesRol/{idRol}/perfil/{idPerfil}")
    public HashMap<String, Object> getFaltantes(@PathVariable int idRol, @PathVariable int idPerfil) {
        HashMap<String, Object> response = new HashMap<>();
        ArrayList<String[]> permisosFaltantes = this.rp.findFaltantesPerfilItem(idRol, idPerfil);
        if (!permisosFaltantes.isEmpty()) {
            response.put("permisos", permisosFaltantes);
            response.put("message", "Permisos cargados");
            response.put("successful", true);
        } else {
            response.put("message", "no hay permisos");
            response.put("successful", false);
        }
        return response;
    }

    //obtiene todos los usuarios
    @GetMapping("/getAllUsers")
    public HashMap<String, Object> getAllUsers() {
        HashMap<String, Object> response = new HashMap<>();
        List<user> users = this.ur.findAll();
        if (!users.isEmpty()) {
            response.put("users", users);
            response.put("message", "usuarios cargados");
            response.put("successful", true);
        } else {
            response.put("message", "no hay usuarios");
            response.put("successful", false);
        }
        return response;
    }

    //obtiene los recursos de un rol y perfil
    @GetMapping("/getRecusosPerfilItem/{idPerfil}/rol/{idRol}")
    public HashMap<String, Object> getRecursosPerfilItem(@PathVariable int idPerfil, @PathVariable int idRol) {
        HashMap<String, Object> response = new HashMap<>();
        List<String[]> data = this.rar.findByperfilItemRecursosAndRol(idPerfil, idRol);
        if (!data.isEmpty()) {
            response.put("recursos", data);
            response.put("message", "recursos cargados");
            response.put("successful", true);
        } else {
            response.put("message", "no hay recursos");
            response.put("successful", false);
        }
        return response;
    }

    //obtine los permisos de un recurso
    @GetMapping("/getRecusosPermisos/{idRecurso}/rol/{idRol}")
    public HashMap<String, Object> getRecursosPermisos(@PathVariable int idRecurso, @PathVariable int idRol) {
        HashMap<String, Object> response = new HashMap<>();
        List<String[]> data = this.par.findPermisosByRecurso(idRol, idRecurso);
        if (!data.isEmpty()) {
            response.put("permisos", data);
            response.put("message", "permisos cargados");
            response.put("successful", true);
        } else {
            response.put("message", "no hay permisos");
            response.put("successful", false);
        }
        return response;
    }

    //obtiene los perfiles faltanes de un rol
    @GetMapping("/getPerfilesRolFaltante/{idRol}")
    public HashMap<String, Object> getPerfilesFaltantes(@PathVariable int idRol) {
        HashMap<String, Object> response = new HashMap<>();

        List<String[]> data = this.pri.getPerfilesRolFaltante(idRol);
        if (!data.isEmpty()) {
            response.put("permisos", data);
            response.put("message", "permisos cargados");
            response.put("successful", true);
        } else {
            response.put("message", "no hay permisos");
            response.put("successful", false);
        }
        return response;
    }

    //obtiene permisos faltante del recurso de un rol
    @GetMapping("/getPermisosRecursosRolFaltante/{idRecurso}/rol/{idRol}")
    public HashMap<String, Object> getPermisosRecursosRolFaltante(@PathVariable int idRecurso, @PathVariable int idRol) {
        HashMap<String, Object> response = new HashMap<>();

        List<String[]> data = this.pr.getPermisionRecursoRolmissed(idRecurso, idRol);
        if (!data.isEmpty()) {
            response.put("permisos", data);
            response.put("message", "permisos cargados");
            response.put("successful", true);
        } else {
            response.put("message", "no hay permisos");
            response.put("successful", false);
        }
        return response;
    }

    //obtiene todos los perfiles
    @GetMapping("/getAllPerfiles")
    public HashMap<String, Object> getAllPerfiles(){
        HashMap<String, Object> response = new HashMap<>();

        List<perfiles> perfiles= this.per.findAll();

        if (!perfiles.isEmpty()) {
            response.put("data", perfiles);
            response.put("message", "perfiles cargados");
            response.put("successful", true);
        } else {
            response.put("message", "no hay permisos");
            response.put("successful", false);
        }
        return response;
    }

    //obtiene todos los perfiles items de un perfil
    @GetMapping("/getAllPerfilesItemsByPerfil/{idPerfil}")
    public HashMap<String, Object> getAllPerfilesItems(@PathVariable int idPerfil){
        HashMap<String, Object> response=new HashMap<>();

        ArrayList<String[]> data = this.pri.getPerfilesItemsByPerfil(idPerfil);

        if (data.isEmpty()){
            response.put("successful", false);
            response.put("message", "Perfiles Items no cargados");
        }else {
            response.put("data", data);
            response.put("successful", true);
            response.put("message", "Perfiles Items cargados");
        }

        return response;
    }

    //obtiene los recursos de un perfil item
    @GetMapping("/getRecursosByPerfilItem/{idPerfilItem}")
    public HashMap<String, Object> getRecursoPerfilItem(@PathVariable int idPerfilItem){
        HashMap<String, Object> response=new HashMap<>();

        ArrayList<String[]> data = this.rp.findByperfilItemRecursos(idPerfilItem);

        if (data.isEmpty()){
            response.put("successful", false);
            response.put("message", "Recursos no cargados");
        }else {
            response.put("data", data);
            response.put("successful", true);
            response.put("message", "Recursos Items cargados");
        }

        return response;
    }

    //obtiene todos los recursos
    @GetMapping("/getAllRecursos")
    public HashMap<String, Object> getAllRecursos(){
        HashMap<String, Object> response=new HashMap<>();

        List<Recursos> data = this.rp.findAll();

        if (data.isEmpty()){
            response.put("successful", false);
            response.put("message", "Recursos no cargados");
        }else {
            response.put("data", data);
            response.put("successful", true);
            response.put("message", "Recursos cargados");
        }

        return response;
    }

    //--------------------------POST-----------------------------------

    //obtiene los permisos de un recurso por su nombre
    @PostMapping("/getpermisosrecurso/rol/{idRol}")
    public HashMap<String, Object> getPermisosRecurso(@PathVariable int idRol, @RequestBody Map<String, String> UrlRecurso){

        HashMap<String, Object> response = new HashMap<>();
        List<String[]> data = this.par.getPermisosRecurso(idRol, UrlRecurso.get("UrlRecurso"));
        if (!data.isEmpty()) {
            response.put("permisos", data);
            response.put("message", "permisos cargados");
            response.put("successful", true);
        } else {
            response.put("message", "no hay permisos");
            response.put("successful", false);
        }
        return response;
    }

    //crear permiso
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createPermissions/{idRecurso}")
    public HashMap create_permission(@RequestBody permission per, @PathVariable int idRecurso){
        HashMap<String,Object> response=new HashMap<>();
        if (this.pr.existsByurl(per.getUrl())){
            response.put("Permissions",null);
            response.put("message","el permiso ya exise");
            response.put("successful",false);
        }
        else {
            if (idRecurso != 0){
                Optional<Recursos> recurso=this.rp.findById(idRecurso);
                if (recurso.isPresent()) per.setRecurso(recurso.get());
            }

            this.pr.save(per);
            response.put("Permissions",per);
            response.put("message","id: "+per.getIdPermission()+" use este id para asignaciones");
            response.put("successful",true);
        }
        return response;
    }

    //añadir permiso a rol
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addpermission/{idperGroup}/rol/{idrol}")
    public HashMap addpermission(@PathVariable(value = "idperGroup") int idperGroup,@PathVariable(value = "idrol") int idrol){
        HashMap<String,Object> response = new HashMap<String,Object>();
        int cambios=this.par.addPermisoGroup(idrol, idperGroup);
        if (cambios==0){
            response.put("message","no se pudo agregar el permiso");
            response.put("successful",false);
        }
        else {
            response.put("message","permiso agregado con exito");
            response.put("successful",true);
        }
        return response;
    }

    //añadir perfil a rol
    @PostMapping("/addPerfilRol/{idRol}/perfil/{idPerfil}")
    public HashMap<String,Object> addPerfilRol(@PathVariable String idRol, @PathVariable int idPerfil){
        HashMap<String, Object> response =new HashMap<>();

        Optional<roles> rol = this.rlr.findById(idRol);
        Optional<PerfilesItems> perfilItem = this.pri.findById(idPerfil);

        if (rol.isPresent() && perfilItem.isPresent()){
            perfilesItemsAsignados pefilAsignado= new perfilesItemsAsignados();
            pefilAsignado.setRol(rol.get());
            pefilAsignado.setPerfilesItems(perfilItem.get());
            this.pia.save(pefilAsignado);
            response.put("message","Perfil Añadido");
            response.put("successful",true);
        }else {
            response.put("message","uno de los datos no existe");
            response.put("successful",false);
        }

        return response;
    }

    //crear rol
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createRole")
    public HashMap create_rol(@RequestBody roles rol){
        HashMap<String,Object> response=new HashMap<String,Object>();
        if (this.rlr.existsByname(rol.getName())){
            response.put("rol",null);
            response.put("message","el rol ya exise");
            response.put("successful",false);
        }
        else {
            roles rolCreado= this.rlr.save(rol);
            this.par.addPermisosRequeridos(rolCreado.getIdRol());
            response.put("rol",rol);
            response.put("message","id rol: "+rol.getIdRol()+" nombre: "+rol.getName());
            response.put("successful",true);
        }
        return response;
    }

    //crear usuario
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public HashMap create_user (@RequestBody user infoUsuario){
        HashMap<String,Object> response=new HashMap<String,Object>();
        if ((this.ur.existsByEmail(infoUsuario.getEmail()) || (this.ur.existsByUsername(infoUsuario.getUsername()))))
        {
            response.put("user",null);
            response.put("message","el usuario o correo ya existe");
            response.put("successful",false);
        }
        else {
            if (infoUsuario.is_valid()){
                infoUsuario.setPassword(passwordEncoder.encode(infoUsuario.getPassword()));
                infoUsuario.setEnabled(Boolean.TRUE);
                Optional<roles> rol= this.rlr.findById("2");
                if (rol.isPresent()){
                    infoUsuario.setRoles(rol.get());
                }
                user save = this.ur.save(infoUsuario);
                infoUsuario.setIdUser(save.getIdUser());
                infoUsuario.setPassword("");
                response.put("user",infoUsuario);
                response.put("message","el usuario fue creado exitosamente");
                response.put("successful",true);
            }
            else {
                response.put("user",null);
                response.put("message","el usuario no pudo ser creado");
                response.put("successful",false);
            }
        }
        return response;
    }

    //añadir recurso a rol
    @PostMapping("/addRecursoRol/{idRecurso}/rol/{idRol}")
    public HashMap<String, Object> addRecursoRol(@PathVariable int idRecurso, @PathVariable String idRol){
        HashMap<String, Object> response=new HashMap<>();
        RecursosAsignados recursosAsignados=new RecursosAsignados();
        Optional<Recursos> recursos = this.rp.findById(idRecurso);
        Optional<roles> rol= this.rlr.findById(idRol);
        if (recursos.isPresent() && rol.isPresent()){
            try {
                recursosAsignados.setRecursos(recursos.get());
                recursosAsignados.setRol(rol.get());
                this.rar.save(recursosAsignados);
                response.put("message","Recurso añadido");
                response.put("successful",true);
            }catch (Exception e){
                response.put("message","el recurso no se pudo añadir error: "+e.getMessage());
                response.put("successful",false);
            }
        }else {
            response.put("message","uno de los datos no existe");
            response.put("successful",false);
        }
        return response;
    }

    //obtiene las rutas
    @PostMapping("/getRut/{idRol}")
    public HashMap<String, Object> getRutas(@RequestBody Map<String, String> recursoP, @PathVariable int idRol) {
        HashMap<String, Object> response=new HashMap<>();
        ArrayList<HashMap<String, String>> listaRutas=new ArrayList<>();
        String recursoUrl=recursoP.get("UrlRecurso");
        if (recursoUrl.contains("ItemsUserPerfil")){
            String[] perfilRecurso=recursoUrl.split("/");
            String perfilItem=perfilRecurso[2];
            int id =Integer.parseInt(perfilItem);
            Optional<PerfilesItems> pefilItem=this.pri.findById(id);

            HashMap<String, String> MapeoRutas=new HashMap<>();
            MapeoRutas.put("route",recursoUrl);
            MapeoRutas.put("label",pefilItem.get().getNombre());
            listaRutas.add(MapeoRutas);
            response.put("data", listaRutas);
            response.put("message","ruta Obtenida");
            response.put("successful",true);
        }else {
            String recurso =(recursoUrl.contains("ItemsUserPerfil"))? "%ItemsUserPerfil%":"%" + recursoUrl + "%";

            String[] ruta= this.pr.getDireccionesRuta(recurso, idRol);

            if (ruta.length>0){
                String[] rutas= ((String) ruta[0]).split(",");
                for (int i=0; i< rutas.length; i+=2){
                    HashMap<String, String> MapeoRutas=new HashMap<>();
                    MapeoRutas.put("route",rutas[i]);
                    MapeoRutas.put("label",rutas[i+1]);
                    listaRutas.add(MapeoRutas);
                }
                response.put("data", listaRutas);
                response.put("message","ruta Obtenida");
                response.put("successful",true);
            }else {
                response.put("message","ruta no Obtenida");
                response.put("successful",false);
            }
        }
        return response;
    }

    @PostMapping("/createPerfil")
    public HashMap createRol(@RequestBody perfiles perfil){
        HashMap<String,Object> response=new HashMap<>();

        if(this.per.existsByNombre(perfil.getNombre())){
            response.put("user",null);
            response.put("message","el Perfil ya existe");
            response.put("successful",false);
        }else{
            this.per.save(perfil);
            response.put("user",perfil);
            response.put("message","Perfil creado");
            response.put("successful",true);
        }
        return response;
    }

    //--------------------------PUT-----------------------------------

    //añadir rol a usuario
    @PutMapping("/rol/{id}/us/{idus}")
    public HashMap addrol(@PathVariable("id") String id, @PathVariable("idus") UUID idus){

        HashMap<String,Object> response=new HashMap<String,Object>();
        Optional<roles> rol =this.rlr.findById(id);
        if (rol.isPresent()){
            user us=this.ur.findById(idus).get();
            us.setRoles(rol.get());
            this.ur.save(us);
            response.put("user",us);
            response.put("message","el rol fuer añadido");
            response.put("successful",true);
        }
        else{
            response.put("user",null);
            response.put("message","el rol no pudo añadido");
            response.put("successful",true);
        }
        return response;
    }

    //actualizar permisos gateway
    @PutMapping("/updatePermision")
    public HashMap<String, Object> updatePerm(@RequestBody String json){
        JSONObject jsonObject = new JSONObject(json);
        String url = jsonObject.getString("url");

        if (url.contains("<")){
            System.out.println("url base de datos: "+url.substring(0, url.indexOf("<")-1) + "/**");
            String newUrl=url.replaceAll("<[^>]*>", "**");
            url = url.substring(0, url.indexOf("<")-1) + "/**";

            Optional<permission> prm = this.pr.findByUrl(url);
            if (prm.isPresent()){
                System.out.println("cambiando...............................................");
                prm.get().setUrl(newUrl);
                this.pr.save(prm.get());
            }
        }
        return new HashMap<>();

    }

    //añadir perfil al perfil
    @PutMapping("/AddPerfilItemUser/{idUser}/Perfil/{idPerfil}")
    public HashMap<String, Object> AddPerfilUser(@PathVariable UUID idUser, @PathVariable int idPerfil){

        Optional<user> userLo= this.ur.findById(idUser);
        if (userLo.isPresent()){
            Optional<PerfilesItems> perfilesItems= this.pri.findById(idPerfil);
            if (perfilesItems.isPresent()) {
                userLo.get().addPerfileItems(perfilesItems.get());
                this.ur.save(userLo.get());
            }
        }

        return new HashMap<>();
    }

    //actualiza un permiso
    @PutMapping("/updatePermission")
    public HashMap<String, Object> UpdatePermission(@RequestBody HashMap<String, Object> update){
        HashMap<String, Object> response=new HashMap<>();
        Optional<permission> permiso= this.pr.findById(Integer.toString((int) update.get("idPermission")));
        if (permiso.isPresent()){
            permission permisoUpdate = permiso.get();

            if ((Boolean) update.get("pertRecurso") && (int) update.get("recursoId") != 0){
                Optional<Recursos> recurso = this.rp.findById((int) update.get("recursoId"));
                if (recurso.isPresent()){
                    permisoUpdate.setRecurso(recurso.get());
                    permisoUpdate.setPertRecurso((Boolean) update.get("pertRecurso"));
                    if (update.get("nombre") != null && update.get("nombre") != "" && update.get("nombre") != "null"){
                        permisoUpdate.setNombre((String) update.get("nombre"));
                    }
                }
            }else{
                permisoUpdate.setRecurso(null);
                permisoUpdate.setPertRecurso((Boolean) update.get("pertRecurso"));
                permisoUpdate.setNombre(null);
            }
            if ((int) update.get("grupoPermiso") != -1 && (int) update.get("grupoPermiso") != 0){
                permisoUpdate.setGrupoPermiso((int) update.get("grupoPermiso"));
            }
            permisoUpdate.setUrlIgnore((Boolean) update.get("urlIgnore"));
            permisoUpdate.setUrl((String) update.get("url"));
            permisoUpdate.setPermisoRequerido((Boolean) update.get("permisoRequerido"));
            permisoUpdate.setMethod((String) update.get("method"));
            permisoUpdate.setNombre((String) update.get("nombre"));

            this.pr.save(permisoUpdate);
            response.put("message","Permiso Actualizado");
            response.put("successful",true);
        } else {
            response.put("message","Permiso no actualizado");
            response.put("successful",false);
        }

        return response;
    }

    @PutMapping("/updatePerfil")
    public HashMap<String, Object> UpdatePerfil(@RequestBody HashMap<String, Object> data){
        HashMap<String, Object> response = new HashMap<>();

        try {
            Optional<perfiles> perfil = this.per.findById((int) data.get("id"));
            if (perfil.isPresent()){
                perfiles perfilesDato=perfil.get();
                perfilesDato.setNombre((String) data.get("nombre"));
                this.per.save(perfilesDato);
                response.put("message","Perfil Actualizado");
                response.put("successful",true);
            }
        }catch (Exception e){
            response.put("message","Perfil no actualizado");
            response.put("successful",false);
        }

        return response;
    }

    //--------------------------DELETE-----------------------------------

    //eliminar recursos de rol
    @DeleteMapping("/deleteRecurso/{idRecurso}/rol/{idRol}")
    public HashMap<String, Object> deleteRecursoRol(@PathVariable int idRecurso, @PathVariable int idRol){
        HashMap<String, Object> response=new HashMap<>();
        try {
            this.rar.deleteRecursoRol(idRol, idRecurso);
            this.par.deletePermisosByRolAndRecurso(idRol, idRecurso);
            response.put("message","permisos eliminado");
            response.put("successful",true);
        }catch (Exception e){
            response.put("message","No se pudo eliminar los permisos: error "+e.getMessage());
            response.put("successful",false);
        }
        return response;
    }

    //eliminar permisos
    @DeleteMapping("/deletePermision/{idPer}")
    public HashMap<String, Object> deletePerm(@PathVariable String idPer){
        HashMap<String, Object> response=new HashMap<>();
        try {
            this.pr.deleteById(idPer);
            response.put("message","Permiso eliminado");
            response.put("successful",true);
        }catch (Exception e){
            response.put("message","No se pudo eliminar el permiso: error "+e.getMessage());
            response.put("successful",false);
        }
        return response;
    }

    //eliminar rol
    @DeleteMapping("/deleteRol/{idRol}")
    public HashMap<String, Object> deleteRol(@PathVariable int idRol){
        HashMap<String, Object> response = new HashMap<>();
        try {
            if (idRol!=1){
                this.par.deleteByIdR(idRol);
                this.pia.deleteByIdR(idRol);
                this.rar.deleteByIdR(idRol);
                this.rlr.deleteById(Integer.toString(idRol));
                response.put("message","Rol Eliminado");
                response.put("successful",true);
            } else {
                response.put("message","No puedes eliminar el rol principal");
                response.put("successful",false);
            }

        } catch (Exception e){
            boolean dato=e.getCause().getMessage().contains("foreign key constraint");
            DataIntegrityViolationException error = (DataIntegrityViolationException) e;

            if (e instanceof DataIntegrityViolationException) {
                if (((NestedRuntimeException) e).getMostSpecificCause().getMessage().contains("foreign key constraint fails")){
                    response.put("message", "No se puede eliminar el rol porque se encuentra en uso");
                }else {
                    response.put("message","El rol no se pudo eliminar error: "+e.getMessage()+" error: "+e.getClass().getName());
                }
            } else {
                response.put("message","El rol no se pudo eliminar error: "+e.getMessage()+" error: "+e.getClass().getName());
            }
            response.put("successful",false);
        }
        return response;
    }

    //eliminar perfil
    @DeleteMapping("/deletePerfilRol/{idPer}/rol/{idRol}")
    public HashMap<String, Object> deletePerfilRol(@PathVariable int idPer, @PathVariable int idRol){
        HashMap<String, Object> response= new HashMap<>();

        try {
            int nm=this.pia.deleteByIdAndRolId(idRol, idPer);
            this.rar.deleteByIdRolAndIdPerfil(idRol, idPer);
            this.par.deleteByIdRAndPerfil(idRol, idPer);
            if(nm!=0){
                response.put("message","perfil Eliminado");
                response.put("successful",true);
            }else {
                response.put("message","perfil no eliminado");
                response.put("successful",false);
            }

        } catch (Exception e){
            response.put("message","No se pudo eliminar el perfil error: "+e.getMessage());
            response.put("successful",false);
        }

        return response;
    }

    //eliminar permiso de rol
    @DeleteMapping("/deletePermiso/{idPer}/rol/{idRol}")
    public HashMap<String, Object> deletePermRol(@PathVariable int idPer, @PathVariable int idRol){
        HashMap<String, Object> response=new HashMap<>();
        try {
            this.par.deletePermiso(idRol,idPer);
            response.put("message","Permiso eliminado");
            response.put("successful",true);
        }catch (Exception e){
            response.put("message","No se pudo eliminar el permiso: error "+e.getMessage());
            response.put("successful",false);
        }
        return response;
    }

    @DeleteMapping("/deletePerfil/{idPerfil}")
    public HashMap<String, Object> deletePerfil(@PathVariable int idPerfil){
        HashMap<String, Object> response=new HashMap<>();

        return response;
    }

    //--------------------------UTILS-----------------------------------
    @Autowired
    private PasswordEncoder passwordEncoder;

}
