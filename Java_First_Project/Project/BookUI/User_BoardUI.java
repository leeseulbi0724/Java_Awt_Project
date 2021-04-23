package BookUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import BookSystem.BookSystem;
import BookVO.BoardVO;
import BookVO.BookVO;
import Commons.Commons;

public class User_BoardUI implements ActionListener, MouseListener {
	JFrame frame;
	User_MainUI main;
	JPanel content_panel;
	JButton btn_write;
	BookSystem system = new BookSystem();
	JTable board_table;
	ArrayList<BoardVO> list;
	String name;
	
	public User_BoardUI(User_MainUI main) {
		this.main = main;
		this.frame = main.f;
		this.name = main.name;
		init();
	}

	
	public void init() {
		main.switching(User_MainUI.BOARD);
		
		content_panel = new JPanel();
		content_panel.setBackground(SystemColor.activeCaption);
		content_panel.setBounds(133, 10, 531, 341);
		content_panel.setLayout(new BorderLayout(0, 0));
		
		String[] colName = {"NO", "분야", "제목", "내용", "작성자", "작성날짜"};
		DefaultTableModel model = new DefaultTableModel(colName, 0);		
		
		JScrollPane scrollPane = new JScrollPane();
		
		board_table = new JTable(model);
		JScrollPane board_pane = new JScrollPane(board_table);
		scrollPane.setViewportView(board_pane);
		
		JLabel label_board = new JLabel("게시판");
		label_board.setHorizontalAlignment(SwingConstants.CENTER);
		content_panel.add(label_board, BorderLayout.NORTH);
		content_panel.add(scrollPane, BorderLayout.CENTER);
		
		JPanel button_panel = new JPanel();
		button_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));		
		button_panel.setBackground(Color.WHITE);
		
		btn_write = new JButton("글쓰기");		
		button_panel.add(btn_write);		
		content_panel.add(button_panel, BorderLayout.SOUTH);
		main.mainPanel.add(content_panel);
		
		label_board.setForeground(new Color(255,255,255));	
		content_panel.setBackground(new Color(255,192,203));
		btn_write.setBackground(Color.WHITE);		
		
		/** 폰트설정 **/
		label_board.setFont(Commons.getFont(25));
		btn_write.setFont(Commons.getFont());	
		
		/** 컬럼 길이조절, 가운데정렬 **/
		board_table.getColumnModel().getColumn(0).setPreferredWidth(1);
		board_table.getColumnModel().getColumn(1).setPreferredWidth(7);
		board_table.getColumnModel().getColumn(3).setPreferredWidth(5);
		board_table.getColumnModel().getColumn(4).setPreferredWidth(5);
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcmSchedule = board_table.getColumnModel();
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
		
		/** 게시판 목록 보여주기 **/
		Object row[];
		 list = system.board_data(); 
		for (BoardVO vo : list) {
			row = new Object[7];
			row[0] = vo.getRownum();
			row[1] = vo.getCategory();
			row[2] = vo.getTitle();
			row[3] = vo.getContent();
			row[4] = vo.getId();
			row[5] = vo.getDate();			
			model.addRow(row);			
		}
		
		/** 이벤트 처리 **/
		btn_write.addActionListener(this);
		board_table.addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(btn_write)) {
			main.mainPanel.removeAll();
			main.mainPanel.setVisible(false);
			new User_WriteUI(main);
		}		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == 1 ) { // 한번 클릭
			  //선택한 셀의 행 번호계산 
//			  int row = board_table.getSelectedRow();		  
			  //테이블의 모델객체 얻어오기
//			  TableModel data = board_table.getModel();		  
			  String bid = list.get(board_table.getSelectedRow()).getBid();
			  BoardVO vo = system.board_result(bid);	
			User_Board_ContentUI ui = new User_Board_ContentUI(vo, name, frame);
			ui.setVisible(true);
		}			
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub		
	}
}

