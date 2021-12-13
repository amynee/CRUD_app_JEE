package web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;

import dao.IProduitDao;
import dao.ProduitDaoImpl;
import metier.entities.Produit;

/**
 * Servlet implementation class ContoleurServlet
 */
@WebServlet (name="cs",urlPatterns= {"/controleur","*.do"})
public class ContoleurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IProduitDao metier;
	
	@Override
	public void init() throws ServletException {
		metier = new ProduitDaoImpl();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContoleurServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		if (path.equals("/index.do")) {
			request.getRequestDispatcher("produits.jsp").forward(request, response);
		} else {
			if (path.equals("/chercher.do")) {
				String motCle = request.getParameter("motCle");
				ProduitModele model = new ProduitModele();
				model.setMotCle(motCle);
				List<Produit> prods = metier.produitsParMC(motCle);
				model.setProduits(prods);
				request.setAttribute("model", model);
				request.getRequestDispatcher("produits.jsp").forward(request, response);
			} else if (path.equals("/saisie.do")) {
				request.getRequestDispatcher("saisieProduit.jsp").forward(request,response);
			}
			else if (path.equals("/save.do") && request.getMethod().equals("POST")) {
				String nom=request.getParameter("nom");
				double prix = Double.parseDouble(request.getParameter("prix"));
				Produit p = metier.save(new Produit(nom,prix));
				request.setAttribute("produit", p);
				request.getRequestDispatcher("confirmation.jsp").forward(request,response);
			} else if (path.equals("/supprimer.do")) {
				Long id= Long.parseLong(request.getParameter("id"));
				metier.deleteProduit(id);
				response.sendRedirect("chercher.do?motCle=");
			} else if (path.equals("/editer.do")) {
				Long id = Long.parseLong(request.getParameter("id"));
				Produit p = metier.getProduit(id);
				request.setAttribute("produit", p);
				request.getRequestDispatcher("editerProduit.jsp").forward(request, response);
			} else if (path.equals("/update.do")) {
				Long id = Long.parseLong(request.getParameter("id"));
				String nom = request.getParameter("nom");
				double prix = Double.parseDouble(request.getParameter("prix"));
				Produit p = new Produit();
				p.setIdProduit(id);
				p.setNomProduit(nom);
				p.setPrix(prix);
				metier.updateProduit(p);
				request.setAttribute("produit", p);
				request.getRequestDispatcher("confirmation.jsp").forward(request, response);
			} else {
				response.sendError(Response.SC_NOT_FOUND);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
