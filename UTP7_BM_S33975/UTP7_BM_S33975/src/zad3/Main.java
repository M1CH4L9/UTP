/**
 *
 *  @author Berlak Michał S33975
 *
 */

package zad3;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;
import java.util.*;

public class Main extends JFrame{
    private DefaultListModel<TaskWrapper> listModel;
    private JList<TaskWrapper> taskList;
    private ExecutorService executorService;

    public Main(){
        super("Task Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        executorService = Executors.newCachedThreadPool();
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);

        add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton btnAdd = new JButton("Add task");
        JButton btnStatus = new JButton("Check status");
        JButton btnCancel = new JButton("Cancel");
        JButton btnResult = new JButton("Show score");

        panel.add(btnAdd);
        panel.add(btnStatus);
        panel.add(btnCancel);
        panel.add(btnResult);
        add(panel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            int time = (int) (Math.random() * 5000) + 1000;
            String name = "Task (time: " + time + "ms)";

            Callable<String> callable = () -> {
                Thread.sleep(time);
                return "Task score " + name;
            };
            Future<String> future = executorService.submit(callable);
            listModel.addElement(new TaskWrapper(name, future));
        });
        btnStatus.addActionListener(e -> {
            TaskWrapper selected = taskList.getSelectedValue();
            if(selected != null){
                Future<?> f = selected.getFuture();
                String status = f.isDone() ? (f.isCancelled() ? "Cancelled" : "Done") : "In progress";
                JOptionPane.showMessageDialog(this, "Status: "+  status);
            }
        });
        btnCancel.addActionListener(e -> {
            TaskWrapper selected = taskList.getSelectedValue();
            if (selected != null) {
                selected.getFuture().cancel(true);
                taskList.repaint();
            }
        });

        btnResult.addActionListener(e -> {
            TaskWrapper selected = taskList.getSelectedValue();
            if (selected != null) {
                Future<String> f = selected.getFuture();
                if (f.isCancelled()) {
                    JOptionPane.showMessageDialog(this, "Task cancelled");
                } else if (f.isDone()) {
                    try {
                        String result = f.get();
                        JOptionPane.showMessageDialog(this, "REsult: " + result);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Task still ongoing...");
                }
            }
        });

        setVisible(true);
    }
    private static class TaskWrapper {
        private String name;
        private Future<String> future;

        public TaskWrapper(String name, Future<String> future) {
            this.name = name;
            this.future = future;
        }

        public Future<String> getFuture() {
            return future;
        }
        @Override
        public String toString() {
            String state = future.isCancelled() ? "[Cancelling]" :
                    (future.isDone() ? "[Done]" : "[In progress]");
            return state + " " + name;
        }
    }

  public static void main(String[] args) {
      SwingUtilities.invokeLater(Main::new);
  }
}
