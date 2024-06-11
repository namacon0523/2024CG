package enshu10;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

public class Paint4 extends Frame implements MouseListener,
									MouseMotionListener,ActionListener{
	int x,y;
	ArrayList<Figure> objList;
	CheckboxGroup cbg;
	Checkbox c1,c2,c3,c4,c5,c6,c7;
	Button end;
	int mode = 0;
	Figure obj;
	MenuBar menuBar;
	Menu fileMenu;
	MenuItem saveMenuItem;
	MenuItem loadMenuItem;
	MenuItem clearAllMenuItem;
	MenuItem startAnimationMenuItem;
	MenuItem deleteMenuItem;
	Menu colorMenu;
	MenuItem chooseColorMenuItem;
	Button undoButton;
	Button redoButton;
	private Stack<ArrayList<Figure>> history;
	private Stack<ArrayList<Figure>> redohistory;
	private Color newColor;
	private int selectedShapeIndex = -1; 



	
	public static void main(String[] args) {
		Paint4 f = new Paint4();
		f.setSize(640,480);
		f.setTitle("Paint Sample");
		f.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				System.exit(0);
		}});
		f.setVisible(true);
		
		if(args.length == 1)f.load(args[0]);
	}
	Paint4(){
		objList = new ArrayList<Figure>();
		setLayout(null);
		//画面作成
		cbg = new CheckboxGroup();
		c1 = new Checkbox("丸",cbg,true);
		c1.setBounds(560,30,60,30);
		add(c1);
		c2 = new Checkbox("円",cbg,false);
		c2.setBounds(560,60,60,30);
		add(c2);
		c3= new Checkbox("四角",cbg,false);
		c3.setBounds(560,90,60,30);
		add(c3);
		c4 = new Checkbox("線",cbg,false);
		c4.setBounds(560,120,60,30);
		add(c4);
		c5 = new Checkbox("楕円",cbg,false);
		c5.setBounds(560,150,60,30);
		add(c5);
		c6 = new Checkbox("三角形", cbg, false);
		c6.setBounds(560, 180, 60, 30);
		add(c6);
		c7 = new Checkbox("六角形", cbg, false);
		c7.setBounds(560, 210, 80, 30);
		add(c7);
		end = new Button("終了");
		end.setBounds(560,330,60,30);
		add(end);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		end.addActionListener(this);
		
		menuBar = new MenuBar();
		setMenuBar(menuBar);
		
		fileMenu = new Menu("ファイル");
		menuBar.add(fileMenu);
		
		saveMenuItem = new MenuItem("保存");
		saveMenuItem.addActionListener(this);
		fileMenu.add(saveMenuItem);
		
		loadMenuItem = new MenuItem("読み込み");
		loadMenuItem.addActionListener(this);
		fileMenu.add(loadMenuItem);
		
		colorMenu = new Menu("色");
		menuBar.add(colorMenu);
		
		chooseColorMenuItem = new MenuItem("色を選択");
		chooseColorMenuItem.addActionListener(this);
		colorMenu.add(chooseColorMenuItem);
		
		clearAllMenuItem = new MenuItem("全ての図形を削除");
		clearAllMenuItem.addActionListener(this);
		fileMenu.add(clearAllMenuItem);
		
		deleteMenuItem = new MenuItem("図形を削除");
		deleteMenuItem.addActionListener(this);
		fileMenu.add(deleteMenuItem);
		
		undoButton = new Button("Undo");
		undoButton.setBounds(560,240,60,30);
		add(undoButton);
		undoButton.addActionListener(this);
		
		redoButton = new Button("Redo");
	    redoButton.setBounds(560, 270, 60, 30);
	    add(redoButton);
	    redoButton.addActionListener(this);
	    
		history = new Stack<>();
		redohistory = new Stack<>();
		history.push(new ArrayList<>());
		
		}
	
	public void save(String fname) {
		try {
			FileOutputStream fos = new FileOutputStream(fname);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(objList);
			oos.close();
			fos.close();
		}catch(IOException e) {
		}
	}@SuppressWarnings("unchecked")
	public void load(String fname) {
		try {
			FileInputStream fis = new FileInputStream(fname);
			ObjectInputStream ois = new ObjectInputStream(fis);
			objList = (ArrayList<Figure>)ois.readObject();
			ois.close();
			fis.close();
		}catch(IOException e) {
		}catch(ClassNotFoundException e) {
		}
		repaint();
	}
	@Override public void paint(Graphics g) {
		Figure f;
		for(int i = 0; i < objList.size(); i++) {
			f = objList.get(i);
			f.paint(g);
		}
		if(mode  >= 1)obj.paint(g);
	}
	@Override public void actionPerformed(ActionEvent e) {
		if(e.getSource()==saveMenuItem) {
			save("paint.dat");
			System.exit(0);
		}else if(e.getSource()==loadMenuItem) {
			load("paint,dat");
			System.exit(0);
		}else if(e.getSource()==end) {
			save("paint.dat");
			System.exit(0);
		}else if(e.getSource()==chooseColorMenuItem) {
			choosePenColor();
			//System.out.println("apple");
		}else if(e.getSource()==undoButton) {
			undo();
		}else if(e.getSource() == redoButton) {
			redo();
		}else if (e.getSource() == clearAllMenuItem) {
	        clearAllShapes();
		}else if (e.getSource() == deleteMenuItem) {
			deleteSelectedShape();
		}
	}
	private int getSelectedShapeIndex(int mouseX, int mouseY) {
	    for (int i = objList.size() - 1; i >= 0; i--) {
	        Figure shape = objList.get(i);
	        if (mouseX >= shape.x && mouseX <= shape.x + shape.getWidth() &&
	            mouseY >= shape.y && mouseY <= shape.y + shape.getHeight()) {
	            return i; 
	        }
	    }
	    return -1; 
	}
	
	private void deleteSelectedShape() {
	    int selectedIndex = chooseShapeToDelete();
	    if (selectedIndex != -1) {
	        objList.remove(selectedIndex);
	        updateHistory();
	        repaint();
	    }
	}
	private int chooseShapeToDelete() {
	    String[] options = new String[objList.size()];
	    for (int i = 0; i < objList.size(); i++) {
	        options[i] = objList.get(i).getName();
	    }

	    String selectedOption = (String) JOptionPane.showInputDialog(
	            this, "削除する図形を選択してください", "図形削除",
	            JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

	    if (selectedOption != null) {
	        for (int i = 0; i < objList.size(); i++) {
	            if (objList.get(i).getName().equals(selectedOption)) {
	                return i;
	            }
	        }
	    }
	    return -1;
	}
	private void clearAllShapes() {
	    objList.clear();
	    history.clear();
	    redohistory.clear();
	    history.push(new ArrayList<>());
	    repaint();
	}
	private void choosePenColor() {
	    newColor = JColorChooser.showDialog(this, "Choose Color", Color.BLACK);
	}
	private void undo() {
		if(!history.isEmpty()&&history.size()>1) {
			redohistory.push(history.pop());
			objList = new ArrayList<>(history.peek());
			repaint();
		}
	}
    private void redo() {
        if (!redohistory.isEmpty()) {
            history.push(redohistory.pop());
            objList = new ArrayList<>(history.peek());
            repaint();
        }
    }
    
	@Override public void mousePressed(MouseEvent e) {
		Checkbox c;
		x = e.getX();
		y = e.getY();
		c = cbg.getSelectedCheckbox();
		obj = null;
			if(c == c1){
				mode = 1;
				obj = new Dot();
			}else if(c == c2) {
				mode = 2;
				obj = new Circle();
			}else if(c == c3) {
				mode = 2;
				obj = new Rect();
			}else if(c == c4) {
				mode = 2;
				obj = new Line();
			}else if(c == c5) {
				mode = 2;
				obj = new Ellipse();
			} else if (c == c6) {
		        mode = 2;
		        obj = new Triangle();
		    }else if (c == c7) {
		        mode = 2;
		        obj = new Hexagon();
		    }
			if(obj!=null) {
				obj.moveto(x,y);
				obj.setColor(newColor);
				repaint();
			}
			selectedShapeIndex = getSelectedShapeIndex(e.getX(), e.getY());
	    }
	@Override public void mouseReleased(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if(mode == 1) obj.moveto(x,y);
		else if(mode == 2)obj.setWH(x - obj.x ,y- obj.y);
		if(mode >= 1) {
			objList.add(obj);
			obj = null;
			updateHistory();
	}
		selectedShapeIndex = getSelectedShapeIndex(e.getX(), e.getY());
		mode = 0;
		repaint();
	}
	
	private void updateHistory() {
		history.push(new ArrayList<>(objList));
			redohistory.clear();
			System.out.println("History updated. Current history size: " + history.size());
		    for (int i = 0; i < history.size(); i++) {
		        System.out.println("History " + i + ": " + history.get(i));
		    }
			repaint();
		}
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mouseDragged(MouseEvent e) {
		x=e.getX();
		y=e.getY();
		if(mode == 1) {
			obj.moveto(x, y);
		}else if(mode == 2) {
			obj.setWH(x-obj.x, y-obj.y);
		}
		repaint();
	}
	@Override public void mouseMoved(MouseEvent e) {}
}