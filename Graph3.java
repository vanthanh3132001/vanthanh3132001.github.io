package Graph3;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.camera.Camera;
import org.graphstream.ui.view.util.InteractiveElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;



public class Graph3 extends JFrame implements ActionListener {
    private JTextArea txtInput;
    private JTextField tHead ,tTail;

    private JButton find,savePath;




    public static void main(String args[]) {


        EventQueue.invokeLater(new Graph3()::display);
    }

    private  void  display(){







        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("Graphxyz", false, true);
        graph.setStrict(false);
        graph.setAutoCreate(true);
        graph.setAttribute("ui.stylesheet", "graph {\n" +
                "\tfill-color: white;\n" +
                "}\n" +
                "\n" +
                "node {\n" +
                "\tsize: 25px;\n" +
                "\tshape: circle;\n" +
                "\tfill-color: white;\n" +
                "\tstroke-mode: plain;\n" +
                "\tstroke-color: black;\n" +
                "}\n" +
                "node.marked {" +
                "fill-color: yellow;" +
                "}"+
                "node.marked2 {" +
                        "fill-color: white;" +
                        "}"+


                "edge {\n" +
                "\tfill-color: black;\n" +
                "\tshape: line;\n" +
                "}\n"+"edge.marked {" +
                "fill-color: red;" +
                "}"+"edge.marked2 {" +
                "fill-color: black;" +
                "}" );
        Scanner sc = new Scanner(System.in);
        List<String> list = new ArrayList<String>();
        Integer x1,x2;
        String s1,s2,s;
        boolean ch1,ch2;
        int u,z;
        DFS g = new DFS(100);


        while (true) {

            s1 = sc.next();
            if (s1.equals("Draw")){
                JFrame frame = new JFrame();
               BorderLayout layoutManager = new BorderLayout();
                JPanel panel = new JPanel(layoutManager){
                    public Dimension getPreferredSize() {
                        return new Dimension(1200, 600);
                    }
                };

                panel.setBorder(BorderFactory.createLineBorder(Color.black, 2));


              panel.setAlignmentX(Component.CENTER_ALIGNMENT);
               panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));


                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("TEAM4_PROJECT");

                // Set margins around control in layout.
                layoutManager.setHgap(0);
                layoutManager.setVgap(0);
                // Create buttons.
                JLabel lHead = new JLabel("Head");
                 tHead = new JTextField(10);
                JLabel lTail = new JLabel("Tail");
                tTail = new JTextField(10);
                 find = new JButton("Find");
                 savePath = new JButton("Save Path");
               // cbHandler = new CalculateButtonHandler();
               // find.addActionListener(cbHandler);
                // Add the buttons to panel.

                for (Node node : graph) {
                    node.setAttribute("ui.label", node.getId());

                }
              //  System.out.print("Start & End: ");
              //  u = sc.nextInt();
               // z = sc.nextInt();
               // System.out.println("All paths: ");
               // g.printAllPaths(u,z);






                SwingViewer view = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
                view.enableAutoLayout();

                ViewPanel viewPanel = (ViewPanel)view.addDefaultView(false);

                viewPanel.removeMouseListener(viewPanel.getMouseListeners()[0]);

                viewPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));


                Camera camera = view.getDefaultView().getCamera();
                camera.setAutoFitView(true);

                viewPanel.addMouseMotionListener(new MouseMotionListener() {

                    private int preX = -1;
                    private int preY = -1;

                    @Override
                    public void mouseDragged(MouseEvent mouseEvent) {
                        int currentX = mouseEvent.getX();
                        int currentY = mouseEvent.getY();

                        Point3 pointView = camera.getViewCenter();

                        if (preX != -1 && preY != -1) {
                            if (preX < currentX) {
                                pointView.x -= 0.01;
                            }
                            else if (preX > currentX) {
                                pointView.x += 0.01;
                            }

                            if (preY < currentY) {
                                pointView.y += 0.01;
                            }
                            else if (preY > currentY) {
                                pointView.y -= 0.01;
                            }
                        }
                        camera.setViewCenter(pointView.x, pointView.y, pointView.z);

                        preX = currentX;
                        preY = currentY;
                    }

                    @Override
                    public void mouseMoved(MouseEvent mouseEvent) {
                        GraphicElement node =  ((View) viewPanel).findGraphicElementAt(EnumSet.of(InteractiveElement.NODE), mouseEvent.getX(), mouseEvent.getY());
                        if (node != null) {
                            viewPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
                        else {
                            viewPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        }
                    }
                });

                viewPanel.addMouseWheelListener(new MouseWheelListener() {
                    @Override
                    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
                        if (mouseWheelEvent.getWheelRotation() < 0) {
                            camera.setViewPercent(camera.getViewPercent() * 0.95);
                        }
                        else {
                            camera.setViewPercent(camera.getViewPercent() * 1.05);
                        }
                    }
                });

                viewPanel.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        GraphicElement node = ((View) viewPanel).findGraphicElementAt(EnumSet.of(InteractiveElement.NODE), mouseEvent.getX(), mouseEvent.getY());
                        if (node != null) {
                            System.out.println(node.getId());
                            graph.getNode(node.getId()).setAttribute("ui.class", "marked");
                        }
                    }
                    @Override
                    public void mousePressed(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseExited(MouseEvent mouseEvent) {
                    }
                });

                panel.add(viewPanel);
                frame.add(lHead);
                frame.add(tHead);
                frame.add(lTail);
                frame.add(tTail);
                frame.add(savePath);
                frame.add(find);




                txtInput = new JTextArea(8,16);
                txtInput.setText("All path!\n");
                JScrollPane jsp = new JScrollPane(txtInput);
                //jsp.setViewportView(find);
                txtInput.setEditable(false);
                frame.add(jsp,BorderLayout.LINE_END);






                find

                        .addActionListener(e-> {

                            g.printAllPaths(Integer.parseInt(tHead.getText()),Integer.parseInt(tTail.getText()));

                            txtInput.setText(
                                     g.getString() +"\nDuong ngan nhat\n "+ g.getValue() );



                            String text = txtInput.getText();
                            txtInput.setCaretPosition(text != null ? text.length() : 0);


                          //  System.out.println(g.getValue().get(0));
                          // System.out.println(g.getValue().size());
                          //  System.out.println(g.getValue().size());

                            int[] List =new int[g.getValue().size()];
                            for(int i=0;i<g.getValue().size();i++){
                               List[i]=(int)(g.getValue().get(i));
                            };










                       //     String[] edgeUpdates = {"12", "23","34"};

                            Timer timer = new Timer(1000, new ActionListener() {
                                int cnt = 0;
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {
                                    if(cnt == List.length) {
                                        ((Timer) actionEvent.getSource()).stop();
                                        return;
                                    }

                                    graph.getNode(""+List[cnt]+"").setAttribute("ui.class", "marked");

                                    graph.getEdge(""+List[cnt]+List[cnt+1]+"").setAttribute("ui.class", "marked");
                                    cnt ++;

                                }
                            });
                            timer.start();














                            g.clp();

                        });


                savePath



                        .addActionListener(ex-> {

                            int[] nodeUpdates =new int[5000];
                            for(int i=0;i<5000;i++){
                                nodeUpdates[i]=i;
                            };
                          //  String[] edgeUpdates = {"12", "23","34"};

                            Timer timer = new Timer(0, new ActionListener() {
                                 int cnt = 0;
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {
                                    if(cnt == 5000) {
                                        ((Timer) actionEvent.getSource()).stop();
                                        return;
                                    }
                                    System.out.println(cnt);
                                    graph.getNode(nodeUpdates[cnt]).setAttribute("ui.class", "marked2");


                                    graph.getEdge(nodeUpdates[cnt]).setAttribute("ui.class", "marked2");
                                    cnt ++;
                                }
                            });



                                    timer.start();






                        });







               // btnBottom.addActionListener(this);

              //  find.addActionListener(this);
              //  this.validate();

                frame.add(panel);
                frame.pack();
                frame.setLayout(new FlowLayout());
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setSize(1200, 600);

                break;
            }
            else {

                ch1 = true;
                ch2 = true;
                s2 =sc.next();
                for (String t : list) {
                    if (t.equals(s1)) ch1 = false;
                    if (t.equals(s2)) ch2 = false;
                }
                if (ch1 == true){
                    graph.addNode(s1);
                    list.add(s1);
                }
                if (ch2 == true){
                    graph.addNode(s2);
                    list.add(s2);
                }
                x1 = Integer.parseInt(s1);
                x2 = Integer.parseInt(s2);
                g.addEd(x1,x2);
                s = s1 + s2;
                graph.addEdge(s,s1,s2,true);


            }




        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


    // @Override



}