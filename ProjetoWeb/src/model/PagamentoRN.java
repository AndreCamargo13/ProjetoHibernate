package model;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class PagamentoRN {

	public static void main(String[] args) {
		
		try
		{
			Scanner entrada = new Scanner(System.in);
			int opcao = 0;
			long cpf, codcurso;
			String datainscricao;
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAApp");
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();	
			
			
			while (opcao != 6) 
			{
				System.out.println("Sistema de Gerenciamento de Pagamentos");
				System.out.println("====================================");
				System.out.println("Digite (1) para consultar todos os Pagamentos");
				System.out.println("Digite (2) para consultar um Pagamento específico");
				System.out.println("Digite (3) para cadastrar um novo Pagamento");
				System.out.println("Digite (4) para alterar um Pagamento");
				System.out.println("Digite (5) para excluir um Pagamento");
				System.out.println("Digite (6) para sair");
				System.out.println("====================================");
				opcao = entrada.nextInt();
				
				switch(opcao)
				{
					case 1: // consultar todos
					{
						System.out.println("[1] consultar todos os Pagamentos");
						//consultarTodos();
						break;
					}
					
					case 2:  //consultar um Pagamento específico
					{
						System.out.println("[2] consultar um Pagamento específico");
						System.out.println("Favor informar o cpf>>>>");
						cpf = entrada.nextLong();
						System.out.println("Favor informar o codcurso>>>>");
						codcurso = entrada.nextLong();
						PagamentoId pgtoid = new PagamentoId(cpf, codcurso);
						Pagamento pagamento = em.find(Pagamento.class, pgtoid);
						System.out.println(pagamento);
						break;
					}
					
					case 3:  //cadastrar Pagamento
					{
						System.out.println("[3] cadastrar um novo Pagamento");
						System.out.println("Favor informar o cpf>>>>");
						cpf = entrada.nextLong();
						System.out.println("Favor informar o codcurso>>>>");
						codcurso = entrada.nextLong();
						PagamentoId pgtoid = new PagamentoId(cpf, codcurso);
						System.out.println("Favor informar a data de inscricao>>>>");
						datainscricao = entrada.nextLine();
						Pagamento pagamento = new Pagamento(pgtoid, datainscricao);
						tx.begin();
						em.persist(pagamento);
						tx.commit();
						break;
					}
					
					case 4:  //alterar Pagamento
					{
						System.out.println("[4] alterar um novo Pagamento");
						System.out.println("Favor informar o cpf>>>>");
						cpf = entrada.nextLong();
						System.out.println("Favor informar o codcurso>>>>");
						codcurso = entrada.nextLong();
						PagamentoId pgtoid = new PagamentoId(cpf, codcurso);
						System.out.println("Favor informar a data de inscricao>>>>");
						datainscricao = entrada.nextLine();
						Pagamento pagamento = new Pagamento(pgtoid, datainscricao);
						tx.begin();
						em.merge(pagamento);
						tx.commit();
						break;
					}
					
					case 5:  //Excluir
					{
						System.out.println("[5] excluir um Pagamento");
						System.out.println("Favor informar o cpf>>>>");
						cpf = entrada.nextLong();
						System.out.println("Favor informar o codcurso>>>>");
						codcurso = entrada.nextLong();
						PagamentoId pgtoid = new PagamentoId(cpf, codcurso);
						Pagamento pagamento = em.find(Pagamento.class, pgtoid);
						em.remove(pagamento);
						break;
					}
					
					case 6: //sair
					{
						System.out.println("Encerrando o sistema...");
						em.close();
						break;
					}
				}
			}	
			entrada.close();
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
