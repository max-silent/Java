/**
* Таблица умножения
*/
import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

//import java.applet.Applet;
public class Multiplying5 extends JApplet implements ActionListener, Runnable{

Font appletFont=new Font("Monospased",Font.BOLD, 20);
Font messageFont=new Font("Arial", Font.BOLD, 20);
Boolean duplicatedAnswerOption;
Thread delayedClean=new Thread(this);
boolean isAnswerCorrect=false;
int attemptsCounter=0, failedAnswersCounter=0;
//Начальный размер апплета
int width=260, height=200;

//Панели выбора множителей
JPanel topContainer=new JPanel();
JPanel topSubContainer=new JPanel();
JPanel topSubContainerLeftPane=new JPanel();
JPanel topSubContainerRightPane=new JPanel();

//Панель задания и вариантов ответов
JPanel middleContainer=new JPanel();

//Панель вывода результата ответа
JPanel bottomContainer=new JPanel();

//Выбираемые множители (все)
JCheckBox multipliers[]=new JCheckBox [10];

//Подтверждение выбора/начало проверки
JButton confirmNumbers=new JButton("Начать проверку");

//Выбранные множители
ArrayList<Integer> selectedNumbers = new ArrayList<Integer>();

//Элемент, отображающий задание
JLabel example = new JLabel();

//Панель с переключателями вариантов ответов
JPanel answerOptionsPane=new JPanel(new GridLayout(1,3));
ButtonGroup answersGroup=new ButtonGroup();
JRadioButton answerOptions[]=new JRadioButton[3];

//Индексы/значения множителей и предлагаемых результатов
int	firstMultiplierIndex, secondMultiplier, proposedResult, correctAnswerIndex;

JButton confirmAnswer=new JButton("Подтвердить");
JLabel answerResultLabel=new JLabel();
JLabel examplesCounterLabel=new JLabel();
JLabel failedAnswersCounterLabel=new JLabel();
JLabel currentMarkLabel=new JLabel();

//Панель с графической иллюстрацией задания
JPanel paintPane=new JPanel(){
	int circleSize;
	public void paintComponent(Graphics graphics){

		super.paintComponent(graphics);
		if (secondMultiplier>0){
			//circleSize=((this.getHeight()/this.getWidth())>=(selectedNumbers.get(firstMultiplierIndex)/secondMultiplier)?(this.getWidth()/secondMultiplier):(this.getHeight()/selectedNumbers.get(firstMultiplierIndex)));
			circleSize=(this.getHeight()/selectedNumbers.get(firstMultiplierIndex)<=(this.getWidth()/secondMultiplier))?(this.getHeight()/selectedNumbers.get(firstMultiplierIndex)):(this.getWidth()/secondMultiplier);			
			graphics.setColor(Color.blue);
			setBackground(Color.GREEN);
			for (int i=0; i<selectedNumbers.get(firstMultiplierIndex); i++){
			for (int j=0; j<secondMultiplier; j++){
				//graphics.fillOval(j*25+25, i*25, 20, 20);
				//graphics.fillOval(((this.getWidth())/secondMultiplier)/2+j*((this.getWidth())/secondMultiplier), ((this.getHeight())/(selectedNumbers.get(firstMultiplierIndex)))/2+i*((this.getHeight())/(selectedNumbers.get(firstMultiplierIndex))), 20, 20);
				//graphics.fillOval(((this.getWidth())/2)-secondMultiplier*12+j*24, (this.getHeight())/2-(selectedNumbers.get(firstMultiplierIndex))*12+i*25, 20, 20);
				//graphics.fillOval(5+j*circleSize+10, 5+i*circleSize+10, circleSize, circleSize);
				graphics.fillOval(((this.getWidth())/2)-(int)(secondMultiplier*(double)circleSize/2)+j*circleSize, (this.getHeight())/2-(int)(selectedNumbers.get(firstMultiplierIndex)*(double)circleSize/2)+i*circleSize, circleSize, circleSize);
			}
			}
		}
		
	}
};

/**
* Метод init – это конструктор апплета
*/
public void init(){
	
	this.setLayout(null);
	//this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
	this.setSize(width, height);
	//this.setBounds(0, 0, width, height);
	topContainer.setBounds(0, 0, width, 175);
	topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
	//topContainer.setLayout(null);
	//topContainer.setSize(260, 175);
	//topContainer.setSize(width, 175);
	//topContainer.setAlignmentX(CENTER_ALIGNMENT);
	topSubContainer.setBorder(BorderFactory.createTitledBorder("Выберите множители:"));
	topSubContainer.setAlignmentX(CENTER_ALIGNMENT);
	topSubContainer.setLayout(new GridLayout(1,2));
	topSubContainerLeftPane.setLayout(new BoxLayout(topSubContainerLeftPane, BoxLayout.Y_AXIS));
	topSubContainerRightPane.setLayout(new BoxLayout(topSubContainerRightPane, BoxLayout.Y_AXIS));

	for (int i=0; i<5; i++){
		multipliers[i]=new JCheckBox();
		multipliers[i].setText("Умножение на "+(i+1));
		multipliers[i].setAlignmentX(LEFT_ALIGNMENT);
		topSubContainerLeftPane.add(multipliers[i]);
		multipliers[i+5]=new JCheckBox();
		multipliers[i+5].setText("Умножение на "+(i+6));
		multipliers[i+5].setAlignmentX(LEFT_ALIGNMENT);
		topSubContainerRightPane.add(multipliers[i+5]);
	}
topSubContainer.add(topSubContainerLeftPane);
topSubContainer.add(topSubContainerRightPane);

topContainer.add(topSubContainer);

confirmNumbers.addActionListener(this);
confirmNumbers.setAlignmentX(CENTER_ALIGNMENT);
//confirmNumbers.setBounds(100, 100, 100, 50);

topContainer.add(confirmNumbers);

this.add(topContainer);

middleContainer.setBounds(0, 200, width, 100);
middleContainer.setLayout(new BoxLayout(middleContainer, BoxLayout.Y_AXIS));
//bottomContainer.setLayout(null);
//middleContainer.setAlignmentX(CENTER_ALIGNMENT);
//bottomContainer.setBackground(Color.BLUE);

//example.setSize(150, 100);
//example.setHorizontalAlignment(SwingConstants.CENTER);
example.setAlignmentX(CENTER_ALIGNMENT);
//answerOptionsPane.setLayout(new GridLayout(1,3));
answerOptionsPane.setAlignmentX(CENTER_ALIGNMENT);
answerOptionsPane.setVisible(false);
//answerOptionsPane.setLayout(new BoxLayout(answerOptionsPane, BoxLayout.LINE_AXIS));
for (int i=0; i<3; i++){
	answerOptions[i]=new JRadioButton();
	//answerOptions[i].setEnabled(false);
	//answerOptions[i].setAlignmentX(Component.CENTER_ALIGNMENT);
	answerOptions[i].setAlignmentX(CENTER_ALIGNMENT);
	answersGroup.add(answerOptions[i]);
	answerOptionsPane.add(answerOptions[i]);
}
//centerPanel.add(answerOptionsPane);
confirmAnswer.setVisible(false);
confirmAnswer.addActionListener(this);
confirmAnswer.setAlignmentX(CENTER_ALIGNMENT);
//confirmAnswer.setBounds(0, 200, width, 50);

middleContainer.add(example);
middleContainer.add(answerOptionsPane);
middleContainer.add(confirmAnswer);
this.add(middleContainer);

bottomContainer.setBounds(0, 320, width, 200);
bottomContainer.setLayout(new BoxLayout(bottomContainer, BoxLayout.Y_AXIS));

//answerResultLabel.setVisible(false);
answerResultLabel.setAlignmentX(CENTER_ALIGNMENT);
answerResultLabel.setFont(messageFont);
examplesCounterLabel.setAlignmentX(LEFT_ALIGNMENT);
failedAnswersCounterLabel.setAlignmentX(LEFT_ALIGNMENT);
currentMarkLabel.setAlignmentX(LEFT_ALIGNMENT);
bottomContainer.add(answerResultLabel);
bottomContainer.add(examplesCounterLabel);
bottomContainer.add(failedAnswersCounterLabel);
bottomContainer.add(currentMarkLabel);
this.add(bottomContainer);

paintPane.setVisible(false);
//paintPane.setBounds(0, 275, 250, 600);
//paintPane.setBounds(width, 0, width, 350);
paintPane.setBounds(topContainer.getWidth(), 0, width, 400);
this.add(paintPane);

delayedClean.start();
}


public void actionPerformed(ActionEvent e) {
JButton theButton = (JButton) e.getSource();
// Это кнопка confirmNumbers ?
if (theButton==confirmNumbers){
	selectedNumbers.clear();
	answerResultLabel.setText("");
	for(int i=0;i<10;i++){
		if (multipliers[i].isSelected()) selectedNumbers.add(i+1);
	};
	if (selectedNumbers.size()>0){
		//attemptsCounter=1;
		clearStatistics();
		attemptsCounter=failedAnswersCounter=0;
		printRandomExample();
		middleContainer.setVisible(true);
		bottomContainer.setVisible(true);
		paintPane.setVisible(true);
	}
	else{
		middleContainer.setVisible(false);
		bottomContainer.setVisible(false);
		paintPane.setVisible(false);
		this.setSize(width, height);
		//answerResultPane.setVisible(false);
		JOptionPane.showConfirmDialog(null, "Нужно выбрать хотя бы один множитель", "Ошибка", JOptionPane.PLAIN_MESSAGE);
	}
}
// Это кнопка confirmAnswer ?
if (theButton ==confirmAnswer){
	//Something
	if (answersGroup.getSelection()!=null){
		clearStatistics();
		//answerResultPane.setVisible(true);
		if ((answersGroup.getSelection().getActionCommand()).equals(Integer.toString(selectedNumbers.get(firstMultiplierIndex)*secondMultiplier))){
			synchronized (answerResultLabel){
			answerResultLabel.setForeground(Color.GREEN);
			answerResultLabel.setText("Правильно !");
			isAnswerCorrect=true;
			answerResultLabel.notify();
			}

			//printRandomExample();
		}
		else{
			synchronized (answerResultLabel){
			answerResultLabel.setForeground(Color.RED);
			answerResultLabel.setText("Неправильно !");
			isAnswerCorrect=false;
			answerResultLabel.notify();
			}
			//example.setText(answersGroup.getSelection().getActionCommand());
		}
	}
	else{
		JOptionPane.showConfirmDialog(null, "Нужно выбрать ответ", "Ошибка", JOptionPane.PLAIN_MESSAGE);
	}
};
} // конец метода actionPerformed

void printRandomExample() {
//do {
this.setSize(topContainer.getWidth()+paintPane.getWidth(),paintPane.getHeight());
confirmAnswer.setVisible(true);
paintPane.setVisible(true);
firstMultiplierIndex = (int) (Math.random() * selectedNumbers.size());
secondMultiplier = (int) ((Math.random() * 10)+1);
example.setFont(appletFont);
example.setText(selectedNumbers.get(firstMultiplierIndex).toString()+" x "+secondMultiplier);

paintPane.removeAll();

//answerOptionsPane.removeAll();
//answersGroup.clearSelection();
correctAnswerIndex=(int)(Math.random() * 3);
for (int i=0; i<3; i++){
	//answerOptions[i].setText(i==correctAnswerIndex?(selectedNumbers.get(firstMultiplierIndex)*secondMultiplier):);
	if (i==correctAnswerIndex){
		//answerOptions[i]=new JRadioButton(String.valueOf(selectedNumbers.get(firstMultiplierIndex)*secondMultiplier));
		answerOptions[i].setText(String.valueOf(selectedNumbers.get(firstMultiplierIndex)*secondMultiplier));
		answerOptions[i].setActionCommand(answerOptions[i].getText());
	}
	else{
		do{
			duplicatedAnswerOption=false;
			proposedResult=((int)selectedNumbers.get(firstMultiplierIndex))*((int)(Math.random()*10) + 1);
			for(JRadioButton k:answerOptions){
				if (k.getActionCommand().equals(String.valueOf(proposedResult))){
					duplicatedAnswerOption=true;
					break;
				}
			}
		}
		while ((proposedResult==selectedNumbers.get(firstMultiplierIndex)*secondMultiplier)||duplicatedAnswerOption);
		//answerOptions[i]=new JRadioButton(String.valueOf(proposedResult));
		answerOptions[i].setText(String.valueOf(proposedResult));
		answerOptions[i].setActionCommand(answerOptions[i].getText());
	}
	paintPane.repaint();
	answersGroup.clearSelection();
}
answerOptionsPane.setVisible(true);
}

//Вывод статистики и текущей оценки
public void printStatistics(int examplesCounter, int failedAnswersCounter){
	examplesCounterLabel.setText("Всего попыток: "+String.valueOf(examplesCounter));
	failedAnswersCounterLabel.setText("Неправильных ответов: "+String.valueOf(failedAnswersCounter));
	int currentMark=(int)(12-11*((double)failedAnswersCounter/examplesCounter));
	if (currentMark<6) currentMarkLabel.setForeground(Color.RED);
	else if (currentMark<9) currentMarkLabel.setForeground(Color.MAGENTA);
	else currentMarkLabel.setForeground(Color.GREEN);
	
	currentMarkLabel.setText("Оценка: "+String.valueOf((int)(12-11*((double)failedAnswersCounter/examplesCounter))));
}
//Убрать вывод статистики
public void clearStatistics(){
	examplesCounterLabel.setText("");
	failedAnswersCounterLabel.setText("");
	currentMarkLabel.setText("");
}

public void run(){

	//while (!answerResultLabel.getText().isEmpty()){
	while (true){
		try{
			synchronized (answerResultLabel){
				answerResultLabel.wait();
			}
			Thread.sleep(2500);
			answerResultLabel.setText("");
			attemptsCounter++;
			if (isAnswerCorrect){
				printRandomExample();
			}
			else{
				failedAnswersCounter++;
			}
			printStatistics(attemptsCounter, failedAnswersCounter);
		}
		catch(InterruptedException e1){
			JOptionPane.showConfirmDialog(null, "Произошла ошибка программы: "+e1.toString(), "Ошибка", JOptionPane.PLAIN_MESSAGE);
		}
	}
}
public static void main(String[] arg)
{
	//new Multiplying();
}
}
//} //
