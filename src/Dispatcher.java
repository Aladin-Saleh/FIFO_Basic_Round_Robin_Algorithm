//package unilasalle.codejava.io; 

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.FileNotFoundException;


class Dispatcher{
	
	public static void main(String[] args) {		
		
		FileInputStream fis;
		Processus proc;
		File f;
		String msg="";
		IOCommandes myIO=new IOCommandes();
		Politique politique = new Politique(myIO);
		int i=0;

		
		if(args.length!=1)
			myIO.ecrireEcran("Nombre d'arguments incorrects !\nEntrez le nom du ficher des processus à traiter :)");
		else {
			myIO.ecrireEcran("\n");
			myIO.ecrireEcran("********************* Bienvenue sur notre menu d'ordonnanceur *********************");
			myIO.ecrireEcran("\n");
			
			f=new File(args[0]);
			String allP=myIO.lireFile(f);
		
			myIO.afficheProcess(allP);
			Processus[] process=myIO.tableProcess(allP);
			
			myIO.ecrireEcran("Choix de la politique\n");
			myIO.ecrireEcran("1 - FIFO Basique\n");
			myIO.ecrireEcran("2 - Round Robin Basique\n");
			myIO.ecrireEcran("3 - Round Robin Priorite\n");

			String choix = myIO.lireEcran();

			if(choix.equals("1"))
				politique.basiqueFIFO(process);
			else if(choix.equals("2"))
				politique.roundRobinBasique(process);
			else if(choix.equals("3"))
				politique.roundRobinPriorite(process);
			else
				myIO.ecrireEcran("Choix incorrect !\n");







		}
		
	 }	 
	 
	
	
}

//la suite à partir d'ici ... 
// myIO.ecrireEcran("---------------Traitement :)---------------\n\n")
// for( i = 1; i < process.length; i++)
// 	{
// 	msg=msg+process[i].nameProc+"		";
// }
// myIO.ecrireEcran(msg+"\n");