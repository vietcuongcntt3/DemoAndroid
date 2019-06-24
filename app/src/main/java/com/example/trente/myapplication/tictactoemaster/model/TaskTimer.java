/**
 *  Lop dem thoi gian choi
 */
package com.example.trente.myapplication.tictactoemaster.model;

import android.app.AlertDialog;
import android.widget.TextView;

import com.example.trente.myapplication.tictactoemaster.Controller;

import java.util.Optional;
import java.util.TimerTask;


public class TaskTimer extends TimerTask {
	TextView labeled;
	boolean stop;
	boolean end;

	public TaskTimer(TextView labeled) {
		this.labeled = labeled;
		this.labeled.setText("30");
	}

	@Override
	public void run() {
//		Platform.runLater(new Runnable() {
//
//			@Override
//			public void run() {
//				if (stop && !end) {
//					end = true;
//					stop = true;
//					dialog("Player " + controller.getPlayerFlag() + " Thua");
//
//				} else {
//					if (!end)
//						as();
//				}
//			}
//		});
	}

	public void as() {
		String x = (Integer.parseInt(labeled.getText().toString()) - 1) + "";
		labeled.setText(x);
		if (x.equals("0"))
			stop = true;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public boolean isStop() {
		return stop;
	}
// ham su dung khi tro choi ket thuc
	public void dialog(String title) {
//		AlertDialog alert = new AlertDialog(AlertType.CONFIRMATION);
//		alert.setTitle("Trò chơi kết thúc");
//		alert.setHeaderText(title);
//		alert.setContentText("Bạn có muốn chơi lại không ?");
//		Optional<ButtonType> result = alert.showAndWait();
//		if (result.get() == ButtonType.OK) {
//			if (controller.getPlayer() instanceof HumanPlayer) {
//				controller.view.replayHuman();
//			} else {
//				controller.view.replayComputer();
//			}
//		} else {
//			// su dung khi chon khong hoac tat hop thoai
//		}
	}

	Controller controller;

	public void setController(Controller controller) {
		this.controller = controller;
	}
}
