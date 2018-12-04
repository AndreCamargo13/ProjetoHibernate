package model;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class CursoRN {

	public static void main(String[] args) {
		
		try
		{
			Scanner entrada = new Scanner(System.in);
			int opcao = 0;
			long codcurso, valor;
			String nome;
			String url;
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAApp");
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();	

			while (opcao != 6) 
			{
				System.out.println("Sistema de Gerenciamento de Cursos");
				System.out.println("====================================");
				System.out.println("Digite (1) para consultar todos os Cursos");
				System.out.println("Digite (2) para consultar um Curso específico");
				System.out.println("Digite (3) para cadastrar um novo Curso");
				System.out.println("Digite (4) para alterar um Curso");
				System.out.println("Digite (5) para excluir um Curso");
				System.out.println("Digite (6) para sair");
				System.out.println("====================================");
				opcao = entrada.nextInt();
				
				switch(opcao)
				{
					case 1: // consultar todos
					{
						System.out.println("[1] consultar todos os Cursos");
						TypedQuery<Curso> query = em.createQuery(""
								+ "Select c from Curso c", Curso.class);
						List<Curso> cursos = query.getResultList();
						cursos.forEach(System.out::println);
						break;
					}
					
					case 2:  //consultar um Curso
					{
						System.out.println("[2] consultar um Curso específico");
						System.out.println("Favor informar o codcurso>>>>");
						codcurso = entrada.nextLong();
						Curso curso = em.find(Curso.class, codcurso);
						System.out.println(curso);
						break;
					}
					
					case 3:  //cadastrar Curso
					{
						System.out.println("[3] cadastrar um novo Curso");
						System.out.println("Favor informar o codcurso>>>>");
						codcurso = entrada.nextLong();
						entrada.nextLine(); //esvaziar o buffer do teclado
						System.out.println("Favor informar o nome>>>>");
						nome = entrada.nextLine();
						System.out.println("Favor informar o valor>>>>");
						valor = entrada.nextLong();
						System.out.println("Favor informar a URL>>>>");
						url = entrada.nextLine();
						Curso curso = new Curso(codcurso, nome, valor, url);
						tx.begin();
						em.persist(curso);
						tx.commit();
						break;
					}
					
					case 4:  //alterar Curso
					{
						System.out.println("[4] alterar um novo Curso");
						System.out.println("Favor informar o codcurso>>>>");
						codcurso = entrada.nextLong();
						entrada.nextLine(); //esvaziar o buffer do teclado
						System.out.println("Favor informar o nome>>>>");
						nome = entrada.nextLine();
						System.out.println("Favor informar o valor>>>>");
						valor = entrada.nextLong();
						System.out.println("Favor informar a url>>>>");
						url = entrada.nextLine();
						Curso curso = new Curso(codcurso, nome, valor, url);
						tx.begin();
						em.merge(curso);
						tx.commit();
						break;
					}
					
					case 5:  //Excluir
					{
						System.out.println("[5] excluir um Curso");
						System.out.println("Favor informar o cdcurso>>>>");
						codcurso = entrada.nextLong();
						Curso curso = em.find(Curso.class, codcurso);
						tx.begin();
						em.remove(curso);
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
