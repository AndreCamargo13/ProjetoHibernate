package model;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


public class ClienteRN {

	public static void main(String[] args) {
		
		try
		{
			Scanner entrada = new Scanner(System.in);
			int opcao = 0;
			long cpf;
			String nome, email;
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAApp");
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();	
			
			while (opcao != 6) 
			{
				System.out.println("Sistema de Gerenciamento de clientes");
				System.out.println("====================================");
				System.out.println("Digite (1) para consultar todos os clientes");
				System.out.println("Digite (2) para consultar um cliente específico");
				System.out.println("Digite (3) para cadastrar um novo cliente");
				System.out.println("Digite (4) para alterar um cliente");
				System.out.println("Digite (5) para excluir um cliente");
				System.out.println("Digite (6) para sair");
				System.out.println("====================================");
				opcao = entrada.nextInt();
				
				switch(opcao)
				{
					case 1: // consultar todos
					{
						System.out.println("[1] consultar todos os clientes");
						TypedQuery<Cliente> query = em.createQuery(""
								+ "Select c from Cliente c", Cliente.class);
						List<Cliente> clientes = query.getResultList();
						clientes.forEach(System.out::println);
						break;
					}
					
					case 2:  //consultar um
					{
						System.out.println("[2] consultar um cliente específico");
						System.out.println("Favor informar o cpf>>>>");
						cpf = entrada.nextLong();
						Cliente cliente = em.find(Cliente.class, cpf);
						System.out.println(cliente);
						break;
					}
					
					case 3:  //cadastrar cliente
					{
						System.out.println("[3] cadastrar um novo cliente");
						System.out.println("Favor informar o cpf>>>>");
						cpf = entrada.nextLong();
						entrada.nextLine(); //esvaziar o buffer do teclado
						System.out.println("Favor informar o nome>>>>");
						nome = entrada.nextLine();
						System.out.println("Favor informar o email>>>>");
						email = entrada.nextLine();
						Cliente cliente = new Cliente(cpf, nome, email);
						tx.begin();
						em.persist(cliente);
						tx.commit();
						break;
					}
					
					case 4:  //alterar cliente
					{
						System.out.println("[4] alterar um novo cliente");
						System.out.println("Favor informar o cpf>>>>");
						cpf = entrada.nextLong();
						entrada.nextLine(); //esvaziar o buffer do teclado
						System.out.println("Favor informar o nome>>>>");
						nome = entrada.nextLine();
						System.out.println("Favor informar o email>>>>");
						email = entrada.nextLine();
						Cliente cliente = new Cliente(cpf, nome, email);
						tx.begin();
						em.merge(cliente);
						tx.commit();
						break;
					}
					
					case 5:  //Excluir
					{
						System.out.println("[5] excluir um cliente");
						System.out.println("Favor informar o cpf>>>>");
						cpf = entrada.nextLong();
						Cliente cliente = em.find(Cliente.class, cpf);
						tx.begin();
						em.remove(cliente);
						tx.commit();
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
