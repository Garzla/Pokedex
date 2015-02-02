
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import models.Habilidad;
import models.Pokedex;
import models.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Menu extends javax.swing.JFrame {

    Session session;
    
    public Menu() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {
        
        UIManager.setLookAndFeel
            ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        initComponents();
        this.setIconImage(new ImageIcon("img/icon.png").getImage());
        this.setTitle("Pokédex & Team Builder");
        this.setResizable(false);
        
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
	session = sf.openSession();
        
        idlabel.setVisible(false);
        nombrelabel.setVisible(false);
        tipolabel.setVisible(false);
        jpTipo1.setVisible(false);
        jpTipo2.setVisible(false);
        habilidadlabel.setVisible(false);
        
        loadPokemon();
        pokedex();
        teamBuilder();
        
        
        // CAMBIO DE PESTAÑA
        jTabbedPane2.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent e) {
                int i = jTabbedPane2.getSelectedIndex();
                switch (i){
                    case 0:
                        titulo.setText("POKÉDEX");
                        break;
                    case 1:
                        titulo.setText("ENTRENADOR");
                        break;
                    case 2:
                        titulo.setText("TEAM BUILDER");
                        break;
                }
            }
            
        });
    }

    DexModel dexmodel = new DexModel();
    ArrayList<Pokedex> pokedex = new ArrayList<>();
    
    public void loadPokemon(){
	
        jListDex.setModel(dexmodel);
        Iterator iter = session.createQuery("from Pokedex Order by id").iterate();
	while(iter.hasNext()){
            Pokedex p = (Pokedex) iter.next();
            pokedex.add(p);
            dexmodel.addElement(p.getId()+". "+p.getNombre());
	}
    }
    
    Pokedex pk; //ELEMENTO SELECCIONADO
    public void pokedex(){
        
        this.jListDex.addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                idlabel.setVisible(true);
                nombrelabel.setVisible(true);
                tipolabel.setVisible(true);
                habilidadlabel.setVisible(true);
                
                pk = pokedex.get(jListDex.getSelectedIndex());
                id.setText(pk.getId()+"");
                nombre.setText(pk.getNombre());
                setTipos(pk);
                habilidad.setText(getNombreHabilidad(pk.getHabilidad()));
                imagen.setIcon(setImagen(pk, 130));
                
                imagen.setSize(130, 130);
                panelimg.setSize(130, 130);
            }   
        });
    }
    
    public String getNombreHabilidad(int id){
        Iterator iter = session.createQuery
            ("from Habilidad where id = '"+id+"'").iterate();
        Habilidad h = (Habilidad) iter.next();
        return h.getDenominacion();
    }
    
    public void setTipos(Pokedex pk){
        if (pk.getTipoByTipo2().getId() == 0){
            jpTipo2.setVisible(false);
            tipo2.setVisible(false);
            jpTipo1.setVisible(true);
            tipo1.setText(pk.getTipoByTipo().getDenominacion());
            paintTipo(1);
        } else {
            jpTipo2.setVisible(true);
            tipo2.setVisible(true);
            jpTipo1.setVisible(true);
            tipo1.setText(pk.getTipoByTipo().getDenominacion());
            tipo2.setText(pk.getTipoByTipo2().getDenominacion());
            paintTipo(2);
        }
    }
    
    public void paintTipo(int x){
        int r = 0, g = 0, b = 0;
        switch (pk.getTipoByTipo().getId()){
            case 1:  r = 192; g = 192; b = 192; break;
            case 2:  r = 102; g = 178; b = 255; break;
            case 3:  r = 178; g = 255; b = 102; break;
            case 4:  r = 102; g = 102; b = 255; break;
            case 5:  r = 255; g = 255; b = 102; break;
            case 6:  r = 204; g = 204; b = 255; break;
            case 7:  r = 255; g = 51;  b = 51;  break;
            case 8:  r = 255; g = 153; b = 204; break;
            case 9:  r = 204; g = 255; b = 255; break;
            case 10: r = 195; g = 80;  b = 31;   break;
            case 11: r = 255; g = 255; b = 255; break;
            case 12: r = 0;   g = 153; b = 0;   break;
            case 13: r = 255; g = 51;  b = 153; break;
            case 14: r = 188; g = 153; b = 92;  break;
            case 15: r = 108; g = 87;  b = 50;  break;
            case 16: r = 249; g = 223; b = 178; break;
            case 17: r = 193; g = 73;  b = 202; break;
            case 18: r = 204; g = 229; b = 255; break;
            case 19: r = 112; g = 164; b = 139; break;
        }
        jpTipo1.setBackground(new Color(r, g, b));
        if (x == 2){
            switch (pk.getTipoByTipo2().getId()){
            case 1:  r = 192; g = 192; b = 192; break;
            case 2:  r = 102; g = 178; b = 255; break;
            case 3:  r = 178; g = 255; b = 102; break;
            case 4:  r = 102; g = 102; b = 255; break;
            case 5:  r = 255; g = 255; b = 102; break;
            case 6:  r = 204; g = 204; b = 255; break;
            case 7:  r = 255; g = 51;  b = 51;  break;
            case 8:  r = 255; g = 153; b = 204; break;
            case 9:  r = 204; g = 255; b = 255; break;
            case 10: r = 195; g = 80;  b = 31;   break;
            case 11: r = 255; g = 255; b = 255; break;
            case 12: r = 0;   g = 153; b = 0;   break;
            case 13: r = 255; g = 51;  b = 153; break;
            case 14: r = 188; g = 153; b = 92;  break;
            case 15: r = 108; g = 87;  b = 50;  break;
            case 16: r = 249; g = 223; b = 178; break;
            case 17: r = 193; g = 73;  b = 202; break;
            case 18: r = 204; g = 229; b = 255; break;
            case 19: r = 112; g = 164; b = 139; break;
        }
        jpTipo2.setBackground(new Color(r, g, b));
        }
    }
    
    public ImageIcon setImagen(Pokedex pk, int x){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("img/"+pk.getId()+".png"));
        } catch (IOException e) {
                e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(x, x, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        return imageIcon;
    }
    
    public ImageIcon setImagenOtros(String name, int x){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("img/"+name+".png"));
        } catch (IOException e) {
                e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(x, x, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        return imageIcon;
    }
    
    static int numPkm = 0;
    Pokedex selPkm;
    public void teamBuilder(){
        
        //PKM1
        pokemon1.setVisible(false);
        pkmNombre.setVisible(false);
        pkmImg.setVisible(false);
        borrar.setVisible(false);
        //PKM1
        pokemon2.setVisible(false);
        pkmNombre10.setVisible(false);
        pkmImg10.setVisible(false);
        borrar10.setVisible(false);
        //PKM1
        pokemon3.setVisible(false);
        pkmNombre11.setVisible(false);
        pkmImg11.setVisible(false);
        borrar11.setVisible(false);
        //PKM1
        pokemon4.setVisible(false);
        pkmNombre12.setVisible(false);
        pkmImg12.setVisible(false);
        borrar12.setVisible(false);
        //PKM1
        pokemon5.setVisible(false);
        pkmNombre13.setVisible(false);
        pkmImg13.setVisible(false);
        borrar13.setVisible(false);
        //PKM1
        pokemon6.setVisible(false);
        pkmNombre14.setVisible(false);
        pkmImg14.setVisible(false);
        borrar14.setVisible(false);
        
        ComboPokemon.removeAllItems();
        for (int i = 0; i < 151; i++){
            ComboPokemon.addItem(pokedex.get(i).getNombre());
        }
        
        ComboPokemon.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                selPkm = pokedex.get(ComboPokemon.getSelectedIndex());
            }
        });
        
        //MOV
        
        add.setIcon(setImagenOtros("pokeball", 67));
    }
    
    public void addPokemon(Pokedex pk, int i){
        switch(i){
            case 0:
                pokemon1.setVisible(true);
                pkmNombre.setVisible(true);
                pkmImg.setVisible(true);
                borrar.setVisible(true);
                pkmNombre.setText(pk.getNombre());
                pkmImg.setIcon(setImagen(pk, 80));
                borrar.setIcon(setImagenOtros("borrar", 15));
                break;
            case 1:
                pokemon2.setVisible(true);
                pkmNombre10.setVisible(true);
                pkmImg10.setVisible(true);
                borrar10.setVisible(true);
                pkmNombre10.setText(pk.getNombre());
                pkmImg10.setIcon(setImagen(pk, 80));
                borrar10.setIcon(setImagenOtros("borrar", 15));
                break;
            case 2:
                pokemon3.setVisible(true);
                pkmNombre11.setVisible(true);
                pkmImg11.setVisible(true);
                borrar11.setVisible(true);
                pkmNombre11.setText(pk.getNombre());
                pkmImg11.setIcon(setImagen(pk, 80));
                borrar11.setIcon(setImagenOtros("borrar", 15));
                break;
            case 3:
                pokemon4.setVisible(true);
                pkmNombre12.setVisible(true);
                pkmImg12.setVisible(true);
                borrar12.setVisible(true);
                pkmNombre12.setText(pk.getNombre());
                pkmImg12.setIcon(setImagen(pk, 80));
                borrar12.setIcon(setImagenOtros("borrar", 15));
                break;
            case 4:
                pokemon5.setVisible(true);
                pkmNombre13.setVisible(true);
                pkmImg13.setVisible(true);
                borrar13.setVisible(true);
                pkmNombre13.setText(pk.getNombre());
                pkmImg13.setIcon(setImagen(pk, 80));
                borrar13.setIcon(setImagenOtros("borrar", 15));
                break;
            case 5:
                pokemon6.setVisible(true);
                pkmNombre14.setVisible(true);
                pkmImg14.setVisible(true);
                borrar14.setVisible(true);
                pkmNombre14.setText(pk.getNombre());
                pkmImg14.setIcon(setImagen(pk, 80));
                borrar14.setIcon(setImagenOtros("borrar", 15));
                break;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        PanelPokedex = new javax.swing.JPanel();
        datos = new javax.swing.JPanel();
        nombrelabel = new javax.swing.JLabel();
        tipolabel = new javax.swing.JLabel();
        idlabel = new javax.swing.JLabel();
        habilidadlabel = new javax.swing.JLabel();
        id = new javax.swing.JLabel();
        nombre = new javax.swing.JLabel();
        habilidad = new javax.swing.JLabel();
        jpTipo2 = new javax.swing.JPanel();
        tipo2 = new javax.swing.JLabel();
        jpTipo1 = new javax.swing.JPanel();
        tipo1 = new javax.swing.JLabel();
        panelimg = new javax.swing.JPanel();
        imagen = new javax.swing.JLabel();
        lista = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListDex = new javax.swing.JList();
        PanelEntrenador = new javax.swing.JPanel();
        nuevo = new javax.swing.JPanel();
        seleccion = new javax.swing.JPanel();
        PanelBuilder = new javax.swing.JPanel();
        equipo = new javax.swing.JPanel();
        pokemon1 = new javax.swing.JPanel();
        pkmImg = new javax.swing.JLabel();
        pkmNombre = new javax.swing.JLabel();
        borrar = new javax.swing.JButton();
        pokemon2 = new javax.swing.JPanel();
        pkmImg10 = new javax.swing.JLabel();
        pkmNombre10 = new javax.swing.JLabel();
        borrar10 = new javax.swing.JButton();
        pokemon3 = new javax.swing.JPanel();
        pkmImg11 = new javax.swing.JLabel();
        pkmNombre11 = new javax.swing.JLabel();
        borrar11 = new javax.swing.JButton();
        pokemon4 = new javax.swing.JPanel();
        pkmImg12 = new javax.swing.JLabel();
        pkmNombre12 = new javax.swing.JLabel();
        borrar12 = new javax.swing.JButton();
        pokemon5 = new javax.swing.JPanel();
        pkmImg13 = new javax.swing.JLabel();
        pkmNombre13 = new javax.swing.JLabel();
        borrar13 = new javax.swing.JButton();
        pokemon6 = new javax.swing.JPanel();
        pkmImg14 = new javax.swing.JLabel();
        pkmNombre14 = new javax.swing.JLabel();
        borrar14 = new javax.swing.JButton();
        pokemon = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ComboPokemon = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        nivel = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        add = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setForeground(new java.awt.Color(0, 0, 0));

        datos.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Datos"));
        datos.setPreferredSize(new java.awt.Dimension(260, 351));

        nombrelabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nombrelabel.setText("Nombre");

        tipolabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tipolabel.setText("Tipo");

        idlabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        idlabel.setText("ID Pokédex");

        habilidadlabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        habilidadlabel.setText("Habilidad");

        id.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        id.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        nombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nombre.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        habilidad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        habilidad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jpTipo2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tipo2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tipo2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jpTipo2Layout = new javax.swing.GroupLayout(jpTipo2);
        jpTipo2.setLayout(jpTipo2Layout);
        jpTipo2Layout.setHorizontalGroup(
            jpTipo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTipo2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(tipo2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpTipo2Layout.setVerticalGroup(
            jpTipo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTipo2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(tipo2))
        );

        jpTipo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tipo1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tipo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jpTipo1Layout = new javax.swing.GroupLayout(jpTipo1);
        jpTipo1.setLayout(jpTipo1Layout);
        jpTipo1Layout.setHorizontalGroup(
            jpTipo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTipo1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(tipo1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpTipo1Layout.setVerticalGroup(
            jpTipo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTipo1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(tipo1))
        );

        javax.swing.GroupLayout panelimgLayout = new javax.swing.GroupLayout(panelimg);
        panelimg.setLayout(panelimgLayout);
        panelimgLayout.setHorizontalGroup(
            panelimgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imagen, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
        );
        panelimgLayout.setVerticalGroup(
            panelimgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imagen, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout datosLayout = new javax.swing.GroupLayout(datos);
        datos.setLayout(datosLayout);
        datosLayout.setHorizontalGroup(
            datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(datosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(habilidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(datosLayout.createSequentialGroup()
                        .addGroup(datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tipolabel)
                            .addComponent(nombrelabel)
                            .addComponent(idlabel)
                            .addComponent(habilidadlabel)
                            .addGroup(datosLayout.createSequentialGroup()
                                .addComponent(jpTipo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jpTipo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 48, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(datosLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(panelimg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        datosLayout.setVerticalGroup(
            datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(datosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(idlabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(id)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombrelabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tipolabel)
                .addGap(2, 2, 2)
                .addGroup(datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpTipo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpTipo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(habilidadlabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(habilidad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(panelimg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lista.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Pokédex"));
        lista.setPreferredSize(new java.awt.Dimension(260, 153));

        jListDex.setBackground(new java.awt.Color(240, 240, 240));
        jListDex.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jListDex);

        javax.swing.GroupLayout listaLayout = new javax.swing.GroupLayout(lista);
        lista.setLayout(listaLayout);
        listaLayout.setHorizontalGroup(
            listaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );
        listaLayout.setVerticalGroup(
            listaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        javax.swing.GroupLayout PanelPokedexLayout = new javax.swing.GroupLayout(PanelPokedex);
        PanelPokedex.setLayout(PanelPokedexLayout);
        PanelPokedexLayout.setHorizontalGroup(
            PanelPokedexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPokedexLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(datos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PanelPokedexLayout.setVerticalGroup(
            PanelPokedexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPokedexLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelPokedexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(datos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lista, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Pokedex", PanelPokedex);

        nuevo.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Selección de entrenador"));
        nuevo.setPreferredSize(new java.awt.Dimension(260, 351));

        javax.swing.GroupLayout nuevoLayout = new javax.swing.GroupLayout(nuevo);
        nuevo.setLayout(nuevoLayout);
        nuevoLayout.setHorizontalGroup(
            nuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        nuevoLayout.setVerticalGroup(
            nuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );

        seleccion.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Nuevo entrenador"));
        seleccion.setPreferredSize(new java.awt.Dimension(260, 153));

        javax.swing.GroupLayout seleccionLayout = new javax.swing.GroupLayout(seleccion);
        seleccion.setLayout(seleccionLayout);
        seleccionLayout.setHorizontalGroup(
            seleccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        seleccionLayout.setVerticalGroup(
            seleccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PanelEntrenadorLayout = new javax.swing.GroupLayout(PanelEntrenador);
        PanelEntrenador.setLayout(PanelEntrenadorLayout);
        PanelEntrenadorLayout.setHorizontalGroup(
            PanelEntrenadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEntrenadorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(seleccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PanelEntrenadorLayout.setVerticalGroup(
            PanelEntrenadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelEntrenadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEntrenadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(seleccion, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Entrenador", PanelEntrenador);

        equipo.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Equipo"));
        equipo.setPreferredSize(new java.awt.Dimension(260, 153));

        pokemon1.setBackground(new java.awt.Color(204, 204, 204));
        pokemon1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        pkmNombre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        pkmNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        borrar.setBackground(new java.awt.Color(204, 204, 204));
        borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pokemon1Layout = new javax.swing.GroupLayout(pokemon1);
        pokemon1.setLayout(pokemon1Layout);
        pokemon1Layout.setHorizontalGroup(
            pokemon1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pokemon1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pokemon1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pokemon1Layout.createSequentialGroup()
                        .addComponent(pkmNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(pokemon1Layout.createSequentialGroup()
                        .addComponent(pkmImg, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
        );
        pokemon1Layout.setVerticalGroup(
            pokemon1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pokemon1Layout.createSequentialGroup()
                .addComponent(pkmNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 8, Short.MAX_VALUE)
                .addGroup(pokemon1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pokemon1Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pokemon1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(pkmImg, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pokemon2.setBackground(new java.awt.Color(204, 204, 204));
        pokemon2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        pkmNombre10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        pkmNombre10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        borrar10.setBackground(new java.awt.Color(204, 204, 204));
        borrar10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrar10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pokemon2Layout = new javax.swing.GroupLayout(pokemon2);
        pokemon2.setLayout(pokemon2Layout);
        pokemon2Layout.setHorizontalGroup(
            pokemon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pokemon2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pokemon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pokemon2Layout.createSequentialGroup()
                        .addComponent(pkmNombre10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(pokemon2Layout.createSequentialGroup()
                        .addComponent(pkmImg10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(borrar10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
        );
        pokemon2Layout.setVerticalGroup(
            pokemon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pokemon2Layout.createSequentialGroup()
                .addComponent(pkmNombre10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pokemon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pokemon2Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(borrar10, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pokemon2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pkmImg10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pokemon3.setBackground(new java.awt.Color(204, 204, 204));
        pokemon3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        pkmNombre11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        pkmNombre11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        borrar11.setBackground(new java.awt.Color(204, 204, 204));
        borrar11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrar11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pokemon3Layout = new javax.swing.GroupLayout(pokemon3);
        pokemon3.setLayout(pokemon3Layout);
        pokemon3Layout.setHorizontalGroup(
            pokemon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pokemon3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pokemon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pokemon3Layout.createSequentialGroup()
                        .addComponent(pkmNombre11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(pokemon3Layout.createSequentialGroup()
                        .addComponent(pkmImg11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(borrar11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
        );
        pokemon3Layout.setVerticalGroup(
            pokemon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pokemon3Layout.createSequentialGroup()
                .addComponent(pkmNombre11, javax.swing.GroupLayout.DEFAULT_SIZE, 12, Short.MAX_VALUE)
                .addGroup(pokemon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pokemon3Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(borrar11, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pokemon3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pkmImg11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pokemon4.setBackground(new java.awt.Color(204, 204, 204));
        pokemon4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        pkmNombre12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        pkmNombre12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        borrar12.setBackground(new java.awt.Color(204, 204, 204));
        borrar12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrar12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pokemon4Layout = new javax.swing.GroupLayout(pokemon4);
        pokemon4.setLayout(pokemon4Layout);
        pokemon4Layout.setHorizontalGroup(
            pokemon4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pokemon4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pokemon4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pokemon4Layout.createSequentialGroup()
                        .addComponent(pkmNombre12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(pokemon4Layout.createSequentialGroup()
                        .addComponent(pkmImg12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(borrar12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
        );
        pokemon4Layout.setVerticalGroup(
            pokemon4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pokemon4Layout.createSequentialGroup()
                .addComponent(pkmNombre12, javax.swing.GroupLayout.DEFAULT_SIZE, 12, Short.MAX_VALUE)
                .addGroup(pokemon4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pokemon4Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(borrar12, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pokemon4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pkmImg12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pokemon5.setBackground(new java.awt.Color(204, 204, 204));
        pokemon5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        pkmNombre13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        pkmNombre13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        borrar13.setBackground(new java.awt.Color(204, 204, 204));
        borrar13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrar13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pokemon5Layout = new javax.swing.GroupLayout(pokemon5);
        pokemon5.setLayout(pokemon5Layout);
        pokemon5Layout.setHorizontalGroup(
            pokemon5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pokemon5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pokemon5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pokemon5Layout.createSequentialGroup()
                        .addComponent(pkmNombre13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(pokemon5Layout.createSequentialGroup()
                        .addComponent(pkmImg13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(borrar13, javax.swing.GroupLayout.PREFERRED_SIZE, 16, Short.MAX_VALUE))))
        );
        pokemon5Layout.setVerticalGroup(
            pokemon5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pokemon5Layout.createSequentialGroup()
                .addComponent(pkmNombre13, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                .addGroup(pokemon5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pokemon5Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(borrar13, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pokemon5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pkmImg13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pokemon6.setBackground(new java.awt.Color(204, 204, 204));
        pokemon6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        pkmNombre14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        pkmNombre14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        borrar14.setBackground(new java.awt.Color(204, 204, 204));
        borrar14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrar14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pokemon6Layout = new javax.swing.GroupLayout(pokemon6);
        pokemon6.setLayout(pokemon6Layout);
        pokemon6Layout.setHorizontalGroup(
            pokemon6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pokemon6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pokemon6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pokemon6Layout.createSequentialGroup()
                        .addComponent(pkmNombre14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(pokemon6Layout.createSequentialGroup()
                        .addComponent(pkmImg14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(borrar14, javax.swing.GroupLayout.PREFERRED_SIZE, 16, Short.MAX_VALUE))))
        );
        pokemon6Layout.setVerticalGroup(
            pokemon6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pokemon6Layout.createSequentialGroup()
                .addComponent(pkmNombre14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pokemon6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pokemon6Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(borrar14, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pokemon6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pkmImg14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout equipoLayout = new javax.swing.GroupLayout(equipo);
        equipo.setLayout(equipoLayout);
        equipoLayout.setHorizontalGroup(
            equipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(equipoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(equipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pokemon5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pokemon1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pokemon3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(equipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pokemon2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pokemon4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pokemon6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        equipoLayout.setVerticalGroup(
            equipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, equipoLayout.createSequentialGroup()
                .addGroup(equipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pokemon1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pokemon2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(equipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pokemon3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pokemon4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(equipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pokemon5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pokemon6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pokemon.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Capturar Pokémon"));
        pokemon.setPreferredSize(new java.awt.Dimension(260, 351));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Elige un pokémon");

        ComboPokemon.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Nivel");

        nivel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nivelActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Movimientos");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Mote");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pokemonLayout = new javax.swing.GroupLayout(pokemon);
        pokemon.setLayout(pokemonLayout);
        pokemonLayout.setHorizontalGroup(
            pokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pokemonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ComboPokemon, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pokemonLayout.createSequentialGroup()
                        .addGroup(pokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pokemonLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextField1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(nivel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pokemonLayout.createSequentialGroup()
                        .addGroup(pokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(pokemonLayout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );
        pokemonLayout.setVerticalGroup(
            pokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pokemonLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ComboPokemon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nivel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(add, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout PanelBuilderLayout = new javax.swing.GroupLayout(PanelBuilder);
        PanelBuilder.setLayout(PanelBuilderLayout);
        PanelBuilderLayout.setHorizontalGroup(
            PanelBuilderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBuilderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(equipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pokemon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PanelBuilderLayout.setVerticalGroup(
            PanelBuilderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBuilderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelBuilderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pokemon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(equipo, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Team Builder", PanelBuilder);

        jPanel6.setBackground(new java.awt.Color(204, 0, 0));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        titulo.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("POKÉDEX");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane2)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void borrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_borrarActionPerformed

    private void borrar10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrar10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_borrar10ActionPerformed

    private void borrar11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrar11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_borrar11ActionPerformed

    private void borrar12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrar12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_borrar12ActionPerformed

    private void borrar13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrar13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_borrar13ActionPerformed

    private void borrar14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrar14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_borrar14ActionPerformed

    private void nivelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nivelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nivelActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    
                    new Menu().setVisible(true);
                    
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox ComboPokemon;
    private javax.swing.JPanel PanelBuilder;
    private javax.swing.JPanel PanelEntrenador;
    private javax.swing.JPanel PanelPokedex;
    private javax.swing.JButton add;
    private javax.swing.JButton borrar;
    private javax.swing.JButton borrar1;
    private javax.swing.JButton borrar10;
    private javax.swing.JButton borrar11;
    private javax.swing.JButton borrar12;
    private javax.swing.JButton borrar13;
    private javax.swing.JButton borrar14;
    private javax.swing.JButton borrar2;
    private javax.swing.JButton borrar3;
    private javax.swing.JButton borrar4;
    private javax.swing.JButton borrar5;
    private javax.swing.JButton borrar6;
    private javax.swing.JButton borrar7;
    private javax.swing.JButton borrar8;
    private javax.swing.JButton borrar9;
    private javax.swing.JPanel datos;
    private javax.swing.JPanel equipo;
    private javax.swing.JPanel equipo1;
    private javax.swing.JPanel equipo2;
    private javax.swing.JPanel equipo3;
    private javax.swing.JPanel equipo4;
    private javax.swing.JPanel equipo5;
    private javax.swing.JPanel equipo6;
    private javax.swing.JPanel equipo7;
    private javax.swing.JLabel habilidad;
    private javax.swing.JLabel habilidadlabel;
    private javax.swing.JLabel id;
    private javax.swing.JLabel idlabel;
    private javax.swing.JLabel imagen;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList jListDex;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel jpTipo1;
    private javax.swing.JPanel jpTipo2;
    private javax.swing.JPanel lista;
    private javax.swing.JTextField nivel;
    private javax.swing.JLabel nombre;
    private javax.swing.JLabel nombrelabel;
    private javax.swing.JPanel nuevo;
    private javax.swing.JPanel panelimg;
    private javax.swing.JLabel pkmImg;
    private javax.swing.JLabel pkmImg1;
    private javax.swing.JLabel pkmImg10;
    private javax.swing.JLabel pkmImg11;
    private javax.swing.JLabel pkmImg12;
    private javax.swing.JLabel pkmImg13;
    private javax.swing.JLabel pkmImg14;
    private javax.swing.JLabel pkmImg2;
    private javax.swing.JLabel pkmImg3;
    private javax.swing.JLabel pkmImg4;
    private javax.swing.JLabel pkmImg5;
    private javax.swing.JLabel pkmImg6;
    private javax.swing.JLabel pkmImg7;
    private javax.swing.JLabel pkmImg8;
    private javax.swing.JLabel pkmImg9;
    private javax.swing.JLabel pkmNombre;
    private javax.swing.JLabel pkmNombre1;
    private javax.swing.JLabel pkmNombre10;
    private javax.swing.JLabel pkmNombre11;
    private javax.swing.JLabel pkmNombre12;
    private javax.swing.JLabel pkmNombre13;
    private javax.swing.JLabel pkmNombre14;
    private javax.swing.JLabel pkmNombre2;
    private javax.swing.JLabel pkmNombre3;
    private javax.swing.JLabel pkmNombre4;
    private javax.swing.JLabel pkmNombre5;
    private javax.swing.JLabel pkmNombre6;
    private javax.swing.JLabel pkmNombre7;
    private javax.swing.JLabel pkmNombre8;
    private javax.swing.JLabel pkmNombre9;
    private javax.swing.JPanel pokemon;
    private javax.swing.JPanel pokemon1;
    private javax.swing.JPanel pokemon2;
    private javax.swing.JPanel pokemon3;
    private javax.swing.JPanel pokemon4;
    private javax.swing.JPanel pokemon5;
    private javax.swing.JPanel pokemon6;
    private javax.swing.JPanel seleccion;
    private javax.swing.JLabel tipo1;
    private javax.swing.JLabel tipo2;
    private javax.swing.JLabel tipolabel;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables

    public class DexModel extends AbstractListModel{
        
        private ArrayList <java.lang.String> datos = new ArrayList();

            @Override
            public int getSize() {
               return datos.size();
            }

            @Override
            public Object getElementAt(int index) {
                return this.datos.get(index);
            }
            
            public void addElement(String element){
                this.datos.add(element);
                this.fireIntervalAdded(this, datos.size()-1, datos.size());
            }
            
            public void removeElement(int pos){
                this.datos.remove(pos);
                this.fireIntervalRemoved(this, datos.size()+1, datos.size());
            }  
    }

}