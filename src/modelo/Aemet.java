package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import excepciones.NoEncontradoException;

public class Aemet {

	private String codigo;
	private String rutaXML;
	private String rutaCodigos;
	private ArrayList<Dia> informacionDias;
	private ArrayList<Hora> informacionHoras;
	private TreeMap<String, String> listaCodigos;
	private TreeMap<String, String> listaCA;
	private TreeMap<String, String> listaProvincias;
	private TreeSet<String> listaNombresCiudades;
	private TreeSet<String> listaNombres;
	private TreeSet<String> listaNombresCA;
	private TreeSet<String> listaNombresProvincias;
	private Dia dia;
	private Hora hora;

	public Aemet() {
		this.codigo = "";
		this.rutaXML = "src/file/xml/";
		this.rutaCodigos = "src/file/text/";
		this.listaCA = new TreeMap<String, String>();
		this.listaProvincias = new TreeMap<String, String>();
		this.listaCodigos = new TreeMap<String, String>();
		this.listaNombresCiudades = new TreeSet<String>();
		this.listaNombres = new TreeSet<String>();
		this.listaNombresCA = new TreeSet<String>();
		this.listaNombresProvincias = new TreeSet<String>();
		this.informacionDias = new ArrayList<Dia>();
		this.informacionHoras = new ArrayList<Hora>();

		guardarCodigosPoblacio();
		descargarDias();
	}

	public ArrayList<Dia> getInformacionDias() {
		return informacionDias;
	}
	public ArrayList<Hora> getInformacionHoras() {
		return informacionHoras;
	}

	public TreeSet<String> getListaCiudades() {
		return listaNombresCiudades;
	}

	public TreeSet<String> getListaProv() {
		return listaNombresProvincias;
	}

	public TreeSet<String> getListaCA() {
		return listaNombresCA;
	}

	public TreeSet<String> getListaNombres() {
		return listaNombres;
	}

	public void buscarPorNombre(String nombre) throws NoEncontradoException {

		setCodigo(buscarCodigo(nombre, listaCodigos));
	}

	public String buscarCodigo(String nombre, TreeMap<String, String> map) throws NoEncontradoException {

		String cod = "";
		for (Entry<String, String> entry : map.entrySet()) {

			if (entry.getKey().equals(nombre)) {
				cod = entry.getValue();
				break;
			}

		}

		if (cod.equals("")) {
			throw new NoEncontradoException();
		}
		return cod;
	}

	public void guardarCiudad(String cod) throws NoEncontradoException {

		String codi = buscarCodigo(cod, listaProvincias).substring(2);

		listaNombresCiudades.clear();
		for (Entry<String, String> entry : listaCodigos.entrySet()) {

			if (codi.equals(entry.getValue().substring(0, 2))) {
				listaNombresCiudades.add(entry.getKey());
			}
		}
	}

	public void guardarProv(String cod) throws NoEncontradoException {

		String codi = buscarCodigo(cod, listaCA);

		guardarLlistes("Provincias.txt", listaProvincias, listaNombresProvincias);

		listaNombresProvincias.clear();
		for (Entry<String, String> entry : listaProvincias.entrySet()) {

			if (entry.getValue().substring(0, 2).equals(codi)) {

				listaNombresProvincias.add(entry.getKey());
			}
		}
	}

	public void guardarCA() {

		this.guardarLlistes("CA.txt", listaCA, listaNombresCA);
	}

	public void guardarCodigosPoblacio() {

		this.guardarLlistes("diccionario24.txt", listaCodigos, listaNombres);

	}

	public void guardarLlistes(String fichero, TreeMap<String, String> map, TreeSet<String> set) {

		String vTmp[];
		try {
			Scanner sc = new Scanner(new File(rutaCodigos + fichero));

			while (sc.hasNext()) {

				vTmp = sc.nextLine().split(";");

				map.put(vTmp[1], vTmp[0]);
				set.add(vTmp[1]);
			}

		} catch (FileNotFoundException e) {
		}
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void descagar(String rutaOnline, String rutaLocal) {

		try {

			URL url = new URL(rutaOnline);

			Scanner sc = new Scanner(url.openStream());
			String fichero = rutaLocal;
			PrintWriter pw = new PrintWriter(new File(fichero));
			String linea;

			while (sc.hasNext()) {

				linea = sc.nextLine();

				pw.println(linea);
			}
			pw.close();
		} catch (IOException e) {
		}
	}

	public void descargarHoras() {
		descagar("https://www.aemet.es/xml/municipios_h/localidad_h_" + codigo + ".xml", rutaXML + "localidad_h.xml");
	}

	public void descargarDias() {
		descagar("https://www.aemet.es/xml/municipios/localidad_" + codigo + ".xml", rutaXML + "localidad.xml");

	}

	public void informacioHoraXML() {
		
		File fFxmlFile = new File(rutaXML + "localidad_h.xml");
		
		informacionHoras.clear();
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fFxmlFile);
		} catch (ParserConfigurationException | SAXException | IOException e) {
		}
		
		doc.getDocumentElement().normalize();
		
		NodeList listaHoras = doc.getElementsByTagName("dia");
		
		int lHoras = listaHoras.getLength();
		for (int i = 0; i < lHoras; i++) {
			
			hora = new Hora();
			Datos dato = new Datos();
			
			// Dia
			Node elementoDia = listaHoras.item(i);
			Element eElement = (Element) elementoDia;

			hora.setFecha(eElement.getAttribute("fecha"));
			
		
			NodeList estadoCieloList = eElement.getElementsByTagName("estado_cielo");
			
			int lEstadoCielo = estadoCieloList.getLength();
			for (int j = 0; j < lEstadoCielo; j++) {
	            Node estadoCieloNode = estadoCieloList.item(j);
	            String horas = estadoCieloNode.getAttributes().item(1).getTextContent(); 
	            String estadoCielo = estadoCieloNode.getAttributes().item(0).getTextContent(); 
	          
	            dato.setDato(estadoCielo);
	            dato.setHora(horas);
	            hora.setHEstadoClima(dato);
	            

	        }
	        
			//Precipitacion
			asignarRangosHoras("precipitacion", eElement);
			//Temperatura
			asignarRangosHoras("temperatura", eElement);
			//Sensacion termica
			asignarRangosHoras("sens_termica", eElement);
			//Humedad relativa
			asignarRangosHoras("humedad_relativa", eElement);
			//Viento
			asignarVientoHora(eElement);
			//Guardar horas
			informacionHoras.add(hora);

		}
	}
	public void asignarVientoHora(Element eElement) {
		Viento viento = new Viento();
		NodeList estadoCieloList = eElement.getElementsByTagName("viento");
		int lEstadoCielo = estadoCieloList.getLength();

		for (int j = 0; j < lEstadoCielo; j++) {
            Node estadoCieloNode = estadoCieloList.item(j);
            Element e = (Element) estadoCieloNode;
            String periodo = estadoCieloNode.getAttributes().item(0).getTextContent(); 
            String horientacion = e.getElementsByTagName("direccion").item(0).getTextContent(); 
            String velocidad = e.getElementsByTagName("velocidad").item(0).getTextContent(); 
          

            viento.setHora(periodo);
            viento.setHorientacion(horientacion);
            viento.setDato(velocidad);
            
            hora.setHViento(viento);
            

        }
	}
	
	public void asignarRangosHoras(String tag,Element eElement) {
		Datos dato = new Datos();
		NodeList estadoCieloList = eElement.getElementsByTagName(tag);
		
		int lEstadoCielo = estadoCieloList.getLength();

		for (int j = 0; j < lEstadoCielo; j++) {
            Node estadoCieloNode = estadoCieloList.item(j);
            String horas = estadoCieloNode.getAttributes().item(0).getTextContent(); 
            String estado = estadoCieloNode.getTextContent(); 
          
            dato.setDato(estado);
            dato.setHora(horas);
            
            switch (tag) {

			case "precipitacion":
				
				hora.setHPrecipitacion(dato);
				break;
			case "temperatura":
				
				hora.setHTemperatura(dato);
				break;
			case "sens_termica":
				
				hora.setHSensacionTermica(dato);
				break;
			case "humedad_relativa":
				
				hora.setHHumedad(dato);
				break;

			default:
				break;
			}
            
        }
	}
	public void informacioDiaXML() {

		File fFxmlFile = new File(rutaXML + "localidad.xml");

		informacionDias.clear();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fFxmlFile);
		} catch (ParserConfigurationException | SAXException | IOException e) {
		}

		try {

			doc.getDocumentElement().normalize();

			NodeList listaDias = doc.getElementsByTagName("dia");

			int lDias = listaDias.getLength();

			for (int i = 0; i < lDias; i++) {

				dia = new Dia();
				// Dia
				Node elementoDia = listaDias.item(i);
				Element eElement = (Element) elementoDia;

				dia.setFecha(eElement.getAttribute("fecha"));
				// Estado cielo

				// Acceder a la lista de estado_cielo específica para este día
				NodeList cieloLista = eElement.getElementsByTagName("estado_cielo");

				// Iterar sobre todos los elementos estado_cielo para este día
				int lCielo =cieloLista.getLength();
				for (int j = 0; j < lCielo; j++) {
					Node cieloNode = cieloLista.item(j);
					Element cieloElement = (Element) cieloNode;
					// Establecer el estado del clima para este día
					dia.setEstadoClima(cieloElement.getAttribute("descripcion"));
					// Salir del bucle una vez que se establezca el estado del clima
					if (!dia.getEstadoClima().isEmpty()) {
						break;
					}
				}

				// Temperatura

				devuelveRangos(eElement, "temperatura", "Temperatura");
				// Sensacio termica
				devuelveRangos(eElement, "sens_termica", "Sensación termica");

				// Precipitacions
				NodeList probPrecipitacion = eElement.getElementsByTagName("prob_precipitacion");

				int lPreci = probPrecipitacion.getLength();
				for (int j = 0; j < lPreci; j++) {

					Node precipitacion = probPrecipitacion.item(j);
					Element ePrec = (Element) precipitacion;

					String periodo = ePrec.getAttribute("periodo");

					String precipitacionText = ePrec.getTextContent() + "";
					if (!precipitacionText.isEmpty() && !periodo.isEmpty()) {

						dia.setRangosPrecipitacion(periodo + " " + precipitacionText);
					}

				}

				// Viento

				NodeList rachaViento = eElement.getElementsByTagName("viento");

				int lViento = rachaViento.getLength();
				for (int j = 0; j < lViento; j++) {

					Node nodoViento = rachaViento.item(j);
					Element eViento = (Element) nodoViento;

					String direccion = eViento.getElementsByTagName("direccion").item(0).getTextContent();
					String velocidad = eViento.getElementsByTagName("velocidad").item(0).getTextContent() + " km/h";
					String periodo = eViento.getAttribute("periodo");
					if (direccion.isEmpty() || velocidad.isEmpty()) {

					} else {

						if (periodo.isEmpty()) {

							periodo = "00-24";
						}
						dia.setRangosViento(periodo + " " + direccion + " " + velocidad);

					}

				}

				// Humedad relativa

				devuelveRangos(eElement, "humedad_relativa", "Humedad");

				// UV

				NodeList nodeListUV = eElement.getElementsByTagName("uv_max");
				Node nodoUV = nodeListUV.item(0);
				Element eUV = (Element) nodoUV;

				if (!(eUV == null)) {

					dia.setUltravioleta(eUV.getTextContent());
				}

				informacionDias.add(dia);
			}
		} catch (NullPointerException e) {

			System.err.println("error");
		}
		this.codigo = "";
	}

	public void devuelveRangos(Element eElement, String tag, String tipo) {

		String listaHoras = "";
		String maxima = "";
		String minima = "";
		NodeList nodeList = eElement.getElementsByTagName(tag);
		Node nodo = nodeList.item(0);
		Element element = (Element) nodo;

		maxima = element.getElementsByTagName("maxima").item(0).getTextContent();
		minima = element.getElementsByTagName("minima").item(0).getTextContent();

		switch (tag) {
		case "temperatura":
			dia.setTemperaturaMaxima(maxima);
			dia.setTemperaturaMinima(minima);

			break;
		case "sens_termica":
			dia.setSensacionMaxima(maxima);
			dia.setSensacionMinima(minima);

			break;
		case "humedad_relativa":
			dia.setHumedadMaxima(maxima);
			dia.setHumedadMinima(minima);
			break;

		default:
			break;
		}
		int lDato = element.getElementsByTagName("dato").getLength();
		for (int j = 0; j < lDato; j++) {

			try {
				String hora = element.getElementsByTagName("dato").item(j).getAttributes().item(0).getTextContent();

				listaHoras = (formatarHoraIndividual(hora) + " "
						+ element.getElementsByTagName("dato").item(j).getTextContent());

				switch (tag) {
				case "temperatura":
					dia.setRangosTemperatura(listaHoras);

					break;
				case "sens_termica":
					dia.setRangosSensacion(listaHoras);

					break;
				case "humedad_relativa":
					dia.setRangosHumedad(listaHoras);

					break;

				default:
					break;
				}
			} catch (NullPointerException e) {
				// TODO: handle exception
			}

		}
	}

	public boolean esAhora(String fecha) {

		return fecha.equals(LocalTime.now().getHour() + "");
	}

	public static String formatarHoraIndividual(String hora) {

		int fecha = Integer.parseInt(hora);
		fecha -= 6;
		String formata = fecha < 10 ? "0" : "";
		formata += fecha + "-" + hora;
		return formata;

	}

}
