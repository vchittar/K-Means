import javax.swing.JFrame;


public class Frame extends JFrame{
	public Frame(String name)
	{
		super(name);
		setSize(700,700);
		add(new Panel());
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
