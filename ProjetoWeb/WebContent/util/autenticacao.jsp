<jsp:directive.page import="java.sql.*"/>
<jsp:declaration>
	static String url = "jdbc:oracle:thin:@localhost:1521:XE";
	static String usuario = "cursoHibernate";
	static String senha = "schema";
	static Connection conexao;
	
	public void init() throws ServletException {
    	try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conexao = DriverManager.getConnection(url,usuario,senha);
			conexao.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
</jsp:declaration>
<jsp:scriptlet>
	String cpfmascara = request.getParameter("cpf");
	
	while ( cpfmascara.contains(".") ) {
		cpfmascara = cpfmascara.replace(".", "");
	}
	
	while ( cpfmascara.contains("-") ) {
		cpfmascara = cpfmascara.replace("-", "");
	}
	
	long cpf = Long.parseLong(cpfmascara);
	String senha = request.getParameter("senha");
	
	// Instrução SQL a ser executada
	String consulta = "SELECT * FROM Login where cpf='"+cpf+"' and senha='"+senha+"'";
	//Criar um Objeto Statement
	Statement statement;
	try {
		statement = conexao.createStatement();
		//Cria um Objeto ResultSet
		ResultSet rs = statement.executeQuery(consulta);
		if(rs.next())  // Usuario autenticado
		{
			session.setAttribute("mensagem", "Usuário Autenticado!");
			session.setAttribute("login", "true");
			response.sendRedirect("../index.jsp");
		}
		else
		{
			session.setAttribute("mensagem", "Usuário não Autenticado! Favor se autenticar.");
			session.setAttribute("login", "false");
			response.sendRedirect("../login.jsp");
		}	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
</jsp:scriptlet>