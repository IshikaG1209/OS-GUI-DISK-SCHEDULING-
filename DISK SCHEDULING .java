import javax.swing.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OS_Project extends JFrame implements ActionListener {

    static int seekOperation_CScan = 0;static int seekOperation_FCFS = 0;static int seekOperation_SJF = 0;static int seekOperation_Scan = 0;
    static int head;static int size;
    JLabel jLabel1 = new JLabel("Disk Scheduling"); JLabel jLabel2 = new JLabel("Enter Size of disk");
    JLabel jLabel3 = new JLabel("Enter starting head position"); JLabel jLabel4 = new JLabel("Enter order of queue");
    JTextField jTextField1 = new JTextField(); JTextField jTextField2 = new JTextField();
    JTextField jTextField3 = new JTextField(); JComboBox jComboBox1 = new JComboBox();
    static JTextArea jTextArea1 = new JTextArea(); JScrollPane jScrollPane1 = new JScrollPane();
    JPanel jPanel1 = new JPanel();JButton jButton1 = new JButton("Clear");
    OS_Project(){
        
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36));jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24));jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24));
        jLabel1.setBounds(286, 6, 344, 48);jLabel2.setBounds(78, 103, 218, 32);
        jLabel3.setBounds(78, 153, 301, 32);jLabel4.setBounds(78, 212, 301, 32);
        jPanel1.setBackground(new java.awt.Color(130,130,130));jPanel1.setBounds(0, 0, 868, 645);
        
        jTextField1.setBounds(431, 103, 355, 31);jTextField2.setBounds(431, 153, 355, 32);
        jTextField3.setBounds(431, 222, 375, 32); jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 18));
        jTextField1.setFont(new java.awt.Font("Cascadia Mono", 0, 18));jTextField2.setFont(new java.awt.Font("Cascadia Mono", 0, 18));
        jTextField3.setFont(new java.awt.Font("Cascadia Mono", 0, 18));jTextArea1.setFont(new java.awt.Font("Cascadia Mono", 0, 18));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FCFS", "SJF", "Scan", "C-Scan" }));
        jComboBox1.setBounds(334, 292, 143, 31);

        jButton1.setBounds(660, 370, 134, 36);jButton1.setFont(new java.awt.Font("Segoe UI", 1, 20));
        jButton1.setFocusable(false);jButton1.addActionListener(this);
        jScrollPane1.setBounds(78, 352, 569, 247);
        jTextArea1.setText("OUTPUT\n");
        jScrollPane1.setViewportView(jTextArea1);
        jComboBox1.addActionListener(this);
        jTextArea1.setEditable(false);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);this.setLayout(null);
        this.setResizable(false);this.setBounds(370, 100, 868, 645);
        this.add(jScrollPane1);this.add(jLabel1);this.add(jLabel2);this.add(jButton1);
        this.add(jLabel3);this.add(jLabel4);this.add(jComboBox1);
        this.add(jTextField1); this.add(jTextField2);this.add(jTextField3);
        this.add(jPanel1);this.setVisible(true); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jComboBox1){
            String selectedItem= jComboBox1.getSelectedItem().toString();
            try{
            size = Integer.parseInt(jTextField1.getText()); head = Integer.parseInt(jTextField2.getText());
            String str = jTextField3.getText(); String []split = str.split("\\s*,\\s*");
            int []queue = new int[split.length];
            for(int i=0;i<split.length;i++){
                queue[i] = Integer.parseInt(split[i]);
            }
            if(head>size-1){
                jTextField2.setText("");
                jTextArea1.append("ERROR\n");
                jTextArea1.append("Invalid Head Value!!\nEnter the value again\n");
                return ;
            }
            for(int i=0;i<queue.length;i++){
                if(queue[i]>size-1){
                    jTextField3.setText("");
                    jTextArea1.append("ERROR\n");
                    jTextArea1.append("queue cannot be greater than disk size!! \n");
                    printArray(queue);jTextArea1.append("Invalid Value at position "+ Math.addExact(i, 1) +"\n");
                    return ;
                }
            }
                switch(selectedItem){
                    case "FCFS":{
                        seekOperation_FCFS =0;
                        calculateFCFS(queue);
                        jTextArea1.append("Total seek operation:"+seekOperation_FCFS+"\n");
                        break;
                    }
                    case "SJF":{
                        seekOperation_SJF = 0;
                        calculateSJF(queue);
                        jTextArea1.append("Total seek operation: "+seekOperation_SJF+"\n");
                        break;
                        
                    }
                    case "Scan":{
                        seekOperation_Scan =0;
                        calculateScan(queue);
                        jTextArea1.append("Total seek operation: "+seekOperation_Scan+"\n");
                        break;
                    }
                    case "C-Scan":{
                        seekOperation_CScan =0;
                        calculateCScan(queue);
                        jTextArea1.append("Total seek operations: "+seekOperation_CScan+"\n");
                        break;
                    }
                }
                
            } catch (Exception exp) {
                jTextArea1.append("ERROR !!\n");
            }
        }
        if(e.getSource()==jButton1){
            jTextArea1.setText("OUTPUT\n");
        }
    }
    public static void main(String[] args) {
        OS_Project project = new OS_Project();
    }
    
    public static int calculateCScan(int[] queue) {
        List<Integer> list = new ArrayList<>();
        for (int i : queue) {
            list.add(i);
        }
        list.add(head);
        Collections.sort(list);
        ArrayList<Integer> sequence = new ArrayList<>();
        int index = list.indexOf(head);
        sequence.add(head);

        for (int i = index; i < list.size() - 1; i++) {
            seekOperation_CScan += Math.abs(list.get(i) - list.get(i + 1));
            jTextArea1.append("Move from " + list.get(i) + " to " + list.get(i + 1) + "\n");
            sequence.add(list.get(i+1));
            
        }
        int s = size-1;
        seekOperation_CScan += size-1 - list.get(list.size() - 1);
        sequence.add(size-1);
        sequence.add(0);
        jTextArea1.append("Move from " + list.get(list.size() - 1) + " to " + s + "\n");
        jTextArea1.append("Move from " + s + " to " + 0 + "\n");
        
        seekOperation_CScan += size-1;
        seekOperation_CScan += list.get(0);
    
        for (int i = 0; i < index-1; i++) {
            if(i==0){
                jTextArea1.append("Move from " + 0 + " to " + list.get(i) + "\n");
                sequence.add(list.get(i));
            }
            seekOperation_CScan += Math.abs(list.get(i) - list.get(i + 1));
            jTextArea1.append("Move from " + list.get(i) + " to " + list.get(i + 1) + "\n");
            sequence.add(list.get(i+1));
        }
        jTextArea1.append("Seek sequence: ");
        for(int c:sequence){
            jTextArea1.append(c+" ");
        }
        jTextArea1.append("\n");
        return seekOperation_CScan;
    }
    public static void printArray(int []arr){
        for(int i:arr){
            jTextArea1.append(i+" ");
        }
        jTextArea1.append("\n");
    }
    public static int calculateFCFS(int arr[]){
    int distance = Math.abs(head-arr[0]);
    seekOperation_FCFS+= distance;
    jTextArea1.append("Move from "+head+" to "+arr[0]+"\n");
 
    for (int i = 0; i < arr.length-1; i++) 
    {
        distance = Math.abs(arr[i]-arr[i+1]);
        seekOperation_FCFS += distance;
        jTextArea1.append("Move from "+arr[i]+" to "+arr[i+1]+"\n");
    }
    jTextArea1.append("Seek Sequence is: ");
    printArray(arr);
    return seekOperation_FCFS;
}
    public static int calculateSJF(int[] queue) {
        List<Integer> remainingRequests = new ArrayList<>();
        List<Integer> sequence = new ArrayList<>();
        for (int i : queue) {
            remainingRequests.add(i);
        }
        
        int currentHead = head;
        remainingRequests.add(currentHead);
        Collections.sort(remainingRequests);
        sequence.add(currentHead);
        while (!remainingRequests.isEmpty()) {
            int minDistance = Integer.MAX_VALUE;
            int closestIndex = 0;
            for (int i = 0; i < remainingRequests.size(); i++) {
                if(remainingRequests.get(i)==currentHead){
                    continue;
                }
                int distance = Math.abs(remainingRequests.get(i) - currentHead);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestIndex = i;
                }
            }
            jTextArea1.append("Move from "+currentHead+" to "+remainingRequests.get(closestIndex)+"\n");
            sequence.add(remainingRequests.get(closestIndex));
            seekOperation_SJF += minDistance;
            currentHead = remainingRequests.get(closestIndex);
            remainingRequests.remove(Integer.valueOf(head));
            remainingRequests.remove(closestIndex);
        }
        jTextArea1.append("Seek sequence: ");
        for(int c:sequence){
            jTextArea1.append(c+" ");
        }
        jTextArea1.append("\n");
        return seekOperation_SJF;
    }
    public static int calculateScan(int[] queue) {
        List<Integer> sortedQueue = new ArrayList<>();
        List<Integer> sequence = new ArrayList<>();
        for (int i : queue) {
            sortedQueue.add(i);
        }
        sortedQueue.add(head);
        Collections.sort(sortedQueue);

        int headIndex = sortedQueue.indexOf(head);
        sequence.add(head);
        for (int i = headIndex+1; i < sortedQueue.size(); i++) {
            seekOperation_Scan += Math.abs(sortedQueue.get(i) - head);
            jTextArea1.append("move from "+head +" to "+sortedQueue.get(i)+"\n");
            head = sortedQueue.get(i);
            sequence.add(head);
            
        }
        int s = size-1;
        seekOperation_Scan += Math.abs(size - 1 - head);
        sequence.add(s);
        jTextArea1.append("move from "+head +" to "+s+"\n");
        jTextArea1.append("move from "+s +" to "+sortedQueue.get(headIndex-1)+"\n");
        seekOperation_Scan += Math.abs(s-sortedQueue.get(headIndex-1));
        head = sortedQueue.get(headIndex-1);
        sequence.add(head);
        for (int i = headIndex - 2; i >= 0; i--) {
            seekOperation_Scan += Math.abs(sortedQueue.get(i) - head);
            jTextArea1.append("move from "+head +" to "+sortedQueue.get(i)+"\n");
            head = sortedQueue.get(i);
            sequence.add(head);
        }
        jTextArea1.append("Seek sequence: ");
        for(int c:sequence){
            jTextArea1.append(c+" ");
        }
        jTextArea1.append("\n");
        return seekOperation_Scan;
    }
}
