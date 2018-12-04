package Controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import model.Cliente;
import model.Curso;
import model.Pagamento;
import model.PagamentoId;


/**
 * Servlet implementation class Controlador
 */
@WebServlet("/Controlador")
public class Controlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAApp");
	EntityManager em = emf.createEntityManager();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controlador() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	this.doPost(req, resp);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		//Criar as variáveis
		int idformulario;  //1-cliente, 2-curso, 3-Pagamentos.
		int tipoformulario; //1.1-consulta...
		String cpfmascara;
		long cpf, codcurso, valor;
		String nome;
		String email;
		String nomecurso, valorcurso, url; 
		String datainscricao;
		
		idformulario = Integer.parseInt(request.getParameter("idformulario"));
		tipoformulario = Integer.parseInt(request.getParameter("tipoformulario"));
		EntityTransaction tx = em.getTransaction();
		HttpSession session = request.getSession();
		
		if (idformulario == 1) //1-clientes
		{
			switch(tipoformulario)
			{
				case 11:  //Consultar Todos 
				{
					System.out.println("[1] Consultar Todos");
					TypedQuery<Cliente> query = em.createQuery(""
							+ "Select c from Cliente c",Cliente.class);
					List<Cliente> clientes = query.getResultList();
					session.setAttribute("mensagem", "Total de Cliente(s): "+clientes.size());
					session.setAttribute("clientes", clientes);
					response.sendRedirect("clientes/consultaTodos.jsp");
					break;
				}	
				case 12:  //Consultar
				{
					
					cpfmascara = request.getParameter("cpf");
					cpfmascara = cpfmascara.replaceAll("[.-]","");
					cpf = Long.parseLong(cpfmascara);
					Cliente cliente = em.find(Cliente.class,cpf);
					
					if(cliente != null) //cliente encontrado
					{
						session.setAttribute("mensagem", "Cliente "+cpf+" Encontrado!");
						session.setAttribute("cliente", cliente);
					}
					else //cliente não existe
					{
						session.setAttribute("mensagem", "Cliente "+cpf+" Não Encontrado!");
						session.setAttribute("cliente", null);
					}
					response.sendRedirect("clientes/resultado.jsp");
					break;
				}
				case 13:  //Cadastrar
				{
					cpfmascara = request.getParameter("cpf");
					cpfmascara = cpfmascara.replaceAll("[.-]","");
					cpf = Long.parseLong(cpfmascara);
					nome = request.getParameter("nome");
					email = request.getParameter("email");
					Cliente cliente = new Cliente(cpf,nome,email);
					tx.begin();
					em.persist(cliente);
					tx.commit(); 
					session.setAttribute("mensagem", "Cliente "+cpf+" Cadastrado!");
					session.setAttribute("cliente", cliente);
					response.sendRedirect("clientes/resultado.jsp");
					break;
				}
				case 14:  //Alterar
				{
					cpfmascara = request.getParameter("cpf");
					cpfmascara = cpfmascara.replaceAll("[.-]","");
					cpf = Long.parseLong(cpfmascara);
					nome = request.getParameter("nome");
					email = request.getParameter("email");
					Cliente cliente = em.find(Cliente.class,cpf);
					if(cliente != null) //cliente encontrado
					{
						cliente = new Cliente(cpf,nome,email);
						tx.begin();
						em.merge(cliente);
						tx.commit();  
						
						session.setAttribute("mensagem", "Cliente "+cpf+" Alterado!");
						session.setAttribute("cliente", cliente);
					}
					else //cliente não existe
					{
						session.setAttribute("mensagem", "Cliente "+cpf+" Não Encontrado! Alteração cancelada!");
						session.setAttribute("cliente", null);
					}
					response.sendRedirect("clientes/resultado.jsp");
					break;
				}
				case 15:  //Excluir 
				{
					cpfmascara = request.getParameter("cpf");
					cpfmascara = cpfmascara.replaceAll("[.-]","");
					cpf = Long.parseLong(cpfmascara);
					out.println("<h2> Clientes => Excluir =>"+cpf);
					Cliente cliente = em.find(Cliente.class,cpf);
					
					if(cliente != null) //cliente encontrado
					{
						tx.begin();
						em.remove(cliente);
						tx.commit();
						session.setAttribute("mensagem", "Cliente "+cpf+" Excluído!");
					}
					else //cliente não existe
						session.setAttribute("mensagem", "Cliente "+cpf+" Não Encontrado! Exclusão cancelada!");
					session.setAttribute("cliente", null);
					response.sendRedirect("clientes/resultado.jsp");
					break;
				}
			}
		}
		else if (idformulario == 2) //2-cursos
		{
			switch(tipoformulario)
			{
				case 21:  //Consultar Todos 
				{
					System.out.println("[1] Consultar Todos");
					TypedQuery<Curso> query = em.createQuery(""
							+ "Select c from Curso c",Curso.class);
					List<Curso> cursos = query.getResultList();
					session.setAttribute("mensagem", "Total de Curso(s): "+cursos.size());
					session.setAttribute("cursos", cursos);
					response.sendRedirect("cursos/consultaTodos.jsp");
					break;
				}	
				case 22:  //Consultar
				{
					codcurso = Long.parseLong(request.getParameter("codcurso"));
					Curso curso = em.find(Curso.class,codcurso);
					
					if(curso != null) //curso encontrado
					{
						session.setAttribute("mensagem", "Curso "+codcurso+" Encontrado!");
						session.setAttribute("curso", curso);
					}
					else //curso não existe
					{
						session.setAttribute("mensagem", "Curso "+codcurso+" Não Encontrado!");
						session.setAttribute("curso", null);
					}
					response.sendRedirect("cursos/resultado.jsp");
					break;
				}
				case 23:  //Cadastrar
				{
					codcurso = Long.parseLong(request.getParameter("codcurso"));
					nomecurso = request.getParameter("nome");
					valorcurso = request.getParameter("valor");
					valor = Long.parseLong(valorcurso);
					url = request.getParameter("site");
					Curso curso = new Curso(codcurso,nomecurso,valor,url);
					tx.begin();
					em.persist(curso);
					tx.commit(); 
					session.setAttribute("mensagem", "Curso "+codcurso+" Cadastrado!");
					session.setAttribute("curso", curso);
					response.sendRedirect("cursos/resultado.jsp");
					break;
				}
				case 24:  //Alterar
				{
					codcurso = Long.parseLong(request.getParameter("codcurso"));
					nomecurso = request.getParameter("nome");
					valorcurso = request.getParameter("valor");
					valor = Long.parseLong(valorcurso);
					url = request.getParameter("site");
					Curso curso = em.find(Curso.class,codcurso);
					if(curso != null) //curso encontrado
					{
						curso = new Curso(codcurso,nomecurso,valor,url);
						tx.begin();
						em.merge(curso);
						tx.commit();  
						
						session.setAttribute("mensagem", "Curso "+codcurso+" Alterado!");
						session.setAttribute("curso", curso);
					}
					else //curso não existe
					{
						session.setAttribute("mensagem", "Curso "+codcurso+" Não Encontrado! Alteração cancelada!");
						session.setAttribute("curso", null);
					}
					response.sendRedirect("cursos/resultado.jsp");
					break;

				}
				case 25:  //Excluir 
				{
					codcurso = Long.parseLong(request.getParameter("codcurso"));
					out.println("<h2> Cursos => Excluir =>"+codcurso);
					Curso curso = em.find(Curso.class,codcurso);
					
					if(curso != null) //curso encontrado
					{
						tx.begin();
						em.remove(curso);
						tx.commit();
						session.setAttribute("mensagem", "Curso "+codcurso+" Excluído!");
					}
					else //curso não existe
						session.setAttribute("mensagem", "Curso "+codcurso+" Não Encontrado! Exclusão cancelada!");
					session.setAttribute("curso", null);
					response.sendRedirect("cursos/resultado.jsp");
					break;
				}
			}
		}
		else if (idformulario == 3) //3-pagamentos
		{
			switch(tipoformulario)
			{
				case 31:  //Consultar Todos 
				{
					System.out.println("[1] Consultar Todos");
					TypedQuery<Pagamento> query = em.createQuery(""
							+ "Select p from Pagamento p",Pagamento.class);
					List<Pagamento> pagamentos = query.getResultList();
					session.setAttribute("mensagem", "Total de Pagamento(s): "+pagamentos.size());
					session.setAttribute("pagamentos", pagamentos);
					response.sendRedirect("pagamentos/consultaTodos.jsp");
					break;
				}	
				case 32:  //Consultar
				{
					cpfmascara = request.getParameter("cpf");
					cpfmascara = cpfmascara.replaceAll("[.-]","");
					cpf = Long.parseLong(cpfmascara);
					codcurso = Long.parseLong(request.getParameter("codcurso"));
					out.println("<h2> Pagamentos => Consultar =>"+cpf+"-"+codcurso);
					
					PagamentoId pgtoid = new PagamentoId(cpf,codcurso);
					Pagamento pagamento = em.find(Pagamento.class, pgtoid);
					
					if(pagamento != null) //Pagamento encontrado
					{
						session.setAttribute("mensagem", "Pagamento "+cpf+" "+codcurso+" Encontrado!");
						session.setAttribute("pagamento", pagamento);
					}
					else //Pagamento não existe
					{
						session.setAttribute("mensagem", "Pagamento "+cpf+" "+codcurso+" Não Encontrado!");
						session.setAttribute("pagamento", null);
					}
					response.sendRedirect("pagamentos/resultado.jsp");
					break;
				}
				case 33:  //Cadastrar
				{
					cpfmascara = request.getParameter("cpf");
					cpfmascara = cpfmascara.replaceAll("[.-]","");
					cpf = Long.parseLong(cpfmascara);
					codcurso = Long.parseLong(request.getParameter("codcurso"));
					datainscricao = request.getParameter("datainscricao");
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate date = LocalDate.parse(datainscricao,formatter);
					DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					
					PagamentoId pgtoid = new PagamentoId(cpf,codcurso);
					Pagamento pagamento = new Pagamento(pgtoid, fmt.format(date));
					tx.begin();
					em.persist(pagamento);
					tx.commit(); 
					session.setAttribute("mensagem", "Pagamento "+cpf+" "+codcurso+" Cadastrado!");
					session.setAttribute("pagamento", pagamento);
					response.sendRedirect("pagamentos/resultado.jsp");
					break;
				}
				case 34:  //Alterar
				{
					cpfmascara = request.getParameter("cpf");
					cpfmascara = cpfmascara.replaceAll("[.-]","");
					cpf = Long.parseLong(cpfmascara);
					codcurso = Long.parseLong(request.getParameter("codcurso"));
					datainscricao = request.getParameter("datainscricao");
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate date = LocalDate.parse(datainscricao,formatter);
					DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					
					PagamentoId pgtoid = new PagamentoId(cpf,codcurso);
					Pagamento pagamento = new Pagamento(pgtoid, datainscricao);
					pagamento = em.find(Pagamento.class,pgtoid);
					
					if(pagamento != null) //Pagamento encontrado
					{
						pagamento = new Pagamento(pgtoid, fmt.format(date));
						tx.begin();
						em.merge(pagamento);
						tx.commit();  
						
						session.setAttribute("mensagem", "Pagamento "+cpf+" "+codcurso+" Alterado!");
						session.setAttribute("pagamento", pagamento);
					}
					else //Pagamento não existe
					{
						session.setAttribute("mensagem", "Pagamento "+cpf+" "+codcurso+" Não Encontrado! Alteração cancelada!");
						session.setAttribute("pagamento", null);
					}
					response.sendRedirect("pagamentos/resultado.jsp");
					break;
				}
				case 35:  //Excluir 
				{
					cpfmascara = request.getParameter("cpf");
					cpfmascara = cpfmascara.replaceAll("[.-]","");
					cpf = Long.parseLong(cpfmascara);
					codcurso = Long.parseLong(request.getParameter("codcurso"));

					PagamentoId pgtoid = new PagamentoId(cpf,codcurso);
					Pagamento pagamento = em.find(Pagamento.class,pgtoid);
					
					if(pagamento != null) //pagamento encontrado
					{
						tx.begin();
						em.remove(pagamento);
						tx.commit();
						session.setAttribute("mensagem", "Pagamento "+cpf+" "+codcurso+" Excluído!");
					}
					else //pagamento não existe
						session.setAttribute("mensagem", "Pagamento "+cpf+" "+codcurso+" Não Encontrado! Exclusão cancelada!");
					session.setAttribute("pagamento", null);
					response.sendRedirect("pagamentos/resultado.jsp");
					break;
				}
			}
		}
		
	}
		
}
