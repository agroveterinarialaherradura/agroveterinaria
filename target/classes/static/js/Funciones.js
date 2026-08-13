/* 
 * Contenido de todas las Variables y Funciones en este JS.
 * Da la funcionalidad a la Página Web Fast Food.
 * creando Controles y botones inteligentes.
 */
var Tabla       = new Array(10);
function Pagar(Total){
    alert("Seguro que quiere Cerrar su Orden?");
    var d = new Date();
    Folio = "FF"  + Total + 2018 + d.getMonth() + d.getDate() + d.getHours() + d.getMinutes() + d.getSeconds();
    document.getElementById("Plantilla").innerHTML = "<center><div style='color: white'>Gracias Por Su Preferencia " + " <br>Su Folio es: " + Folio + " por un Monto de " + Total + " </div></center>";
    document.getElementById("Tablas").innerHTML = "";
    document.getElementById("CuerpoPedidos").innerHTML = "Gracias por su Compra";
    document.getElementById("Productos").selectd= "";
    Suma=0;
    gContador =1;
    EncabezadoTabla="";
    Tabla.length = 0;
}

function uCiclo()
{
    var cmdPagarButon = "window.addEventListener('load', function(){window.cookieconsent.initialise({"+
                        "'palette': {'popup': {'background': '#000000'},'button': {'background': '#e30b50'}},"+
                        "'theme': 'classic','position': 'bottom',"+
                        "'content': {'message: 'Presione el Botón para Pagar .','dismiss': 'Aceptar','link': 'Leer más',"+
                        "'href': 'https://www.FastFud.com/cookies/'}})})";
    FilasTablas = "";
    EncabezadoTabla = "<tr><td>#</td><td>C</td><td>Descripcion y Total de Compra $" + Suma + " pesos </td><td>$</td></tr>";
    FilaCompraTabla = "<tr><td></td><td></td><td>"+
    "<button onclick='EnviaPago()' method='post' style='color: red;'>Pagar Monto: $" +  Suma + "</button></td><td></td></tr>";

    Tabla[0] = ['#', 'C', 'Descripcion y Total de Compra', '$'];
    //FilasTablas = "<tr><td>" + Tabla[0][0] + "</td><td>" + Tabla[0][1] + "</td><td>" + Tabla[0][2] + "</td><td>" + Tabla[0][3] + " Total: $" + Suma + "</td></tr>";;
    FilasTablas = EncabezadoTabla;
    for (i = 1; i <= gContador; i++)
    {
      FilasTablas = FilasTablas + "<tr><td>" + Tabla[i][0] + "</td><td>" + Tabla[i][1] + "</td><td>" + Tabla[i][2] + "</td><td>" + Tabla[i][3] + "</td></tr>";
    }
    FilasTablas = FilasTablas + FilaCompraTabla;
    //FilasTablas = FilasTablas + cmdPagarButon;
    //alert("Tablas: " + gContador);
}

function AgregaCarrito(Parametros)
{
    //var Renglon   = new Array(4);
    Suma        = Suma + cvePrecio;
    Tabla[gContador]= [gContador, 1, DescripcionP, cvePrecio];
    uCiclo();
    var CadenaTabla = "<table class='table table-dark'><tbody>" + FilasTablas + "</tbody></table>";
    var Limpiar = "<center><div style='color: white;' id='Plantilla' width='100%'>"+
                  "Enviado al Carrito Correctamente<br>Sigue Comprando"
                  "</div></center>";
    document.getElementById("Tablas").innerHTML = CadenaTabla;
    document.getElementById("Plantilla").innerHTML = Limpiar;
    //document.getElementById("Productos").size=10;
    gContador = gContador +1;
}

function clickSwitch(Parametro)
{
    //alert("clickSwitch: " + Parametro);
    DescripcionP = "";
    switch(Parametro)
    {
        case "Burrito":
            if (document.getElementById("COrden").checked==true)
            DescripcionP = "Orden(s) de (2)Burritos ";
            if (document.getElementById("SOrden").checked==true)
            DescripcionP = "Burrito(s) ";
            if (document.getElementById("Pollo").checked==true)
            DescripcionP = DescripcionP + "de Pollo ";
            if (document.getElementById("Pollo").checked == true && document.getElementById("COrden").checked == true)
            cvePrecio = 25;
            if (document.getElementById("Pollo").checked == true && document.getElementById("SOrden").checked == true)
            cvePrecio = 15;

            if (document.getElementById("Bistec").checked==true)                
            DescripcionP = DescripcionP + "de Bistec ";
            if (document.getElementById("Bistec").checked == true && document.getElementById("COrden").checked == true)
            cvePrecio = 30;
            if (document.getElementById("Bistec").checked == true && document.getElementById("SOrden").checked == true)
            cvePrecio = 18;

            if (document.getElementById("Pastor").checked==true)
            DescripcionP = DescripcionP + "de Pastor ";
            if (document.getElementById("Pastor").checked == true && document.getElementById("COrden").checked == true)
            cvePrecio = 24;
            if (document.getElementById("Pastor").checked == true && document.getElementById("SOrden").checked == true)
            cvePrecio = 20;

            if (document.getElementById("Longaniza").checked==true)
            DescripcionP = DescripcionP + "de Longaniza ";
            if (document.getElementById("Longaniza").checked == true && document.getElementById("COrden").checked == true)
            cvePrecio = 23;
            if (document.getElementById("Longaniza").checked == true && document.getElementById("SOrden").checked == true)
            cvePrecio = 18;

            if (document.getElementById("SLechuga").checked==true)
            DescripcionP = DescripcionP + "Sin Lechuga ";
            if (document.getElementById("SCrema").checked)
            DescripcionP = DescripcionP + "Sin Crema ";
            if (document.getElementById("SQueso").checked)
            DescripcionP = DescripcionP + "Sin Queso ";
            DescripcionP = DescripcionP + "$" + cvePrecio;
        break;
        case "Chilaquiles":
            cvePrecio = 33;
            DescripcionP = "Chilaquiles ";
            if (document.getElementById("SCrema").checked)
            DescripcionP = DescripcionP + "Sin Crema ";
            if (document.getElementById("SQueso").checked)
            DescripcionP = DescripcionP + "Sin Queso ";        
            if (document.getElementById("SPan").checked)
            DescripcionP = DescripcionP + "y Sin Pan ";
            DescripcionP = DescripcionP + "$" + cvePrecio;
        break;
        case "Enchilada":
            DescripcionP = "Enchiladas ";
            if (document.getElementById("Suizas").checked)
            DescripcionP = DescripcionP + "Suizas ";
            if (document.getElementById("Suizas").checked)
            cvePrecio = 27;
            if (document.getElementById("Verdes").checked)
            DescripcionP = DescripcionP + "Verdes ";
            if (document.getElementById("Verdes").checked)
            cvePrecio = 28;
            if (document.getElementById("Mole").checked)
            DescripcionP = DescripcionP + "de Mole ";
            if (document.getElementById("Mole").checked)
            cvePrecio = 30;
            if (document.getElementById("SCrema").checked)
            DescripcionP = DescripcionP + "Sin Crema ";
            if (document.getElementById("SQueso").checked)
            DescripcionP = DescripcionP + "Sin Queso ";        
            if (document.getElementById("SCebolla").checked)
            DescripcionP = DescripcionP + "Sin Cebolla ";
            if (document.getElementById("SSalsa").checked)
            DescripcionP = DescripcionP + "y Sin Salsa ";
            DescripcionP = DescripcionP + "$" + cvePrecio;
        break;
        case "Hamburguesa":
            DescripcionP = "Hamburguesa Sencilla ";
            if (document.getElementById("Sencilla").checked)
            DescripcionP = "Hamburguesa Sencilla ";
            if (document.getElementById("Sencilla").checked)
            cvePrecio = 25;
            if (document.getElementById("Tocino").checked)
            DescripcionP = "Hamburguesa con Tocino ";
            if (document.getElementById("Tocino").checked)
            cvePrecio = 28;
            if (document.getElementById("Quesillo").checked)
            DescripcionP = "Hamburguesa con Quesillo ";
            if (document.getElementById("Quesillo").checked)
            cvePrecio = 32;
            if (document.getElementById("TocinoQueso").checked)
            DescripcionP = "Hamburguesa con Tocino y Quesillo ";
            if (document.getElementById("Quesillo").checked)
            cvePrecio = 35;
            if (document.getElementById("Hawaiano").checked)
            DescripcionP = "Hamburguesa Hawaiana ";
            if (document.getElementById("Hawaiano").checked)
            cvePrecio = 37;
            if (document.getElementById("Res").checked)
            DescripcionP = DescripcionP + "de Res ";        
            if (document.getElementById("Res").checked)
            cvePrecio = 23 + 10;
            if (document.getElementById("Pollo").checked)
            DescripcionP = DescripcionP + "de Pollo ";
            if (document.getElementById("Pollo").checked)
            cvePrecio = 22 + 8;
            if (document.getElementById("Sirloin").checked)
            DescripcionP = DescripcionP + "de Sirloin ";
            if (document.getElementById("Sirloin").checked)
            cvePrecio = 21 + 12;

            if (document.getElementById("SCrema").checked)
            DescripcionP = DescripcionP + "Sin Crema ";
            if (document.getElementById("SSalsa").checked)
            DescripcionP = DescripcionP + "Sin Salsa ";
            DescripcionP = DescripcionP + "$" + cvePrecio;
        break;
        case "HotDog":
            if (document.getElementById("COrden").checked)
            DescripcionP = "Orden(s) de (3)Hot Dogs ";
            if (document.getElementById("SOrden").checked)
            DescripcionP = "Hot Dog ";
            if (document.getElementById("Sencillo").checked)
            DescripcionP = DescripcionP + "Sencillo(s) ";
            if (document.getElementById("Tocino").checked)
            DescripcionP = DescripcionP + "con Tocino ";
            if (document.getElementById("TocinoQueso").checked)
            DescripcionP = DescripcionP + "con Tocino con Queso ";
            if (document.getElementById("Hawaiano").checked)
            DescripcionP = DescripcionP + "Hawaiano ";        
            if (document.getElementById("SMayonesa").checked)
            DescripcionP = DescripcionP + "Sin Mayonesa ";
            if (document.getElementById("SMostaza").checked)
            DescripcionP = DescripcionP + "Sin Mostaza ";
            if (document.getElementById("SCatsup").checked)
            DescripcionP = DescripcionP + "Sin Catsup ";
            if (document.getElementById("SCatsup").checked)
            DescripcionP = DescripcionP + "Sin Catsup ";
            if (document.getElementById("SJitomate").checked)
            DescripcionP = DescripcionP + "Sin Jitomate ";
            if (document.getElementById("SChile").checked)
            DescripcionP = DescripcionP + "Sin Chile ";
            DescripcionP = DescripcionP + " $" + cvePrecio;
        break;
        case "Molletes":
            if (document.getElementById("COrden").checked)
            DescripcionP = "Orden(s) de (3)Molletes ";
            if (document.getElementById("COrden").checked)
            cvePrecio = 22;
            if (document.getElementById("SOrden").checked)
            DescripcionP = "Mollete ";
            if (document.getElementById("SOrden").checked)
            cvePrecio = 13;

            if (document.getElementById("SCrema").checked)
            DescripcionP = DescripcionP + "Sin Crema ";
            if (document.getElementById("SQueso").checked)
            DescripcionP = DescripcionP + "Sin Queso ";        
            if (document.getElementById("SCebolla").checked)
            DescripcionP = DescripcionP + "Sin Cebolla ";
            if (document.getElementById("SSalsa").checked)
            DescripcionP = DescripcionP + "Sin Salsa ";
            DescripcionP = DescripcionP + " $" + cvePrecio;
        break;
        case "Pambazo":
            cvePrecio = 18;
            DescripcionP = "Pambazo con Todo";
            if (document.getElementById("SLechuga").checked)
            DescripcionP = DescripcionP + "Sin Lechuga ";
            if (document.getElementById("SCrema").checked)
            DescripcionP = DescripcionP + "Sin Crema ";
            if (document.getElementById("SQueso").checked)
            DescripcionP = DescripcionP + "Sin Queso ";
            DescripcionP = DescripcionP + " $" + cvePrecio;
        break;
        case "Quesadilla":
            if (document.getElementById("COrden").checked)
            DescripcionP = "Orden(s) de (3)Quesadillas ";
            if (document.getElementById("COrden").checked)
            cvePrecio = 33;
            if (document.getElementById("SOrden").checked)
            DescripcionP = "Quesadilla ";
            if (document.getElementById("SOrden").checked)
            cvePrecio = 11;
        
            if (document.getElementById("SLechuga").checked)
            DescripcionP = DescripcionP + "Sin Lechuga ";
            if (document.getElementById("SCrema").checked)
            DescripcionP = DescripcionP + "Sin Crema ";
            if (document.getElementById("SQueso").checked)
            DescripcionP = DescripcionP + "Sin Queso ";
            DescripcionP = DescripcionP + " $" + cvePrecio;
        break;
        case "Sincronizada":
            cvePrecio = 12;
            DescripcionP = "Sincronizadas ";
            if (document.getElementById("SCatsup").checked)
            DescripcionP = DescripcionP + "Sin Catsup ";
            if (document.getElementById("SMayonesa").checked)
            DescripcionP = DescripcionP + "Sin Mayonesa ";
            if (document.getElementById("SJitomate").checked)
            DescripcionP = DescripcionP + "Sin Jitomate ";
            if (document.getElementById("SChile").checked)
            DescripcionP = DescripcionP + "Sin Chile ";
            DescripcionP = DescripcionP + " $" + cvePrecio;
        break;
        case "Sopa":
            cvePrecio = 13;
            DescripcionP = "Sopa con Todo";
            if (document.getElementById("SChipotle").checked)
            DescripcionP = DescripcionP + "Sin Chipotle ";
            if (document.getElementById("SRajas").checked)
            DescripcionP = DescripcionP + "Sin Rajas ";
            DescripcionP = DescripcionP + " $" + cvePrecio;
        break;
        case "TacoDorado":
            if (document.getElementById("COrden").checked)
            DescripcionP = "Orden(s) de (3)Tacos Dorados ";
            if (document.getElementById("COrden").checked)
            cvePrecio = 25;
            if (document.getElementById("SOrden").checked)
            DescripcionP = "Taco Dorado ";
            if (document.getElementById("SOrden").checked)
            cvePrecio = 9;

            if (document.getElementById("SLechuga").checked)
            DescripcionP = DescripcionP + "Sin Lechuga ";
            if (document.getElementById("SCrema").checked)
            DescripcionP = DescripcionP + "Sin Crema ";
            if (document.getElementById("SQueso").checked)
            DescripcionP = DescripcionP + "Sin Queso ";
            DescripcionP = DescripcionP + " $" + cvePrecio;
        break;
        case "Torta":
            if (document.getElementById("Res").checked)
            DescripcionP = "Torta de Res ";
            if (document.getElementById("Res").checked)
            cvePrecio = 28;
            if (document.getElementById("Pollo").checked)
            DescripcionP = "Torta de Pollo ";
            if (document.getElementById("Pollo").checked)
            cvePrecio = 25;
            if (document.getElementById("Pastor").checked)
            DescripcionP = "Torta de Pastor ";
            if (document.getElementById("Pastor").checked)
            cvePrecio = 27;
            if (document.getElementById("Salchicha").checked)
            DescripcionP = "Torta de Salchicha ";
            if (document.getElementById("Salchicha").checked)
            cvePrecio = 24;
            if (document.getElementById("Pierna").checked)
            DescripcionP = "Torta de Pierna ";
            if (document.getElementById("Pierna").checked)
            cvePrecio = 29;
            if (document.getElementById("Cubana").checked)
            DescripcionP = "Torta de Cubana ";
            if (document.getElementById("Cubana").checked)
            cvePrecio = 30;
            if (document.getElementById("SChipotle").checked)
            DescripcionP = DescripcionP + "Sin Chipotle ";
            if (document.getElementById("SRajas").checked)
            DescripcionP = DescripcionP + "Sin Rajas ";
            if (document.getElementById("SLechuga").checked)
            DescripcionP = DescripcionP + "Sin Lechuga ";
            if (document.getElementById("SJitomate").checked)
            DescripcionP = DescripcionP + "Sin Jitomate ";
            DescripcionP = DescripcionP + " $" + cvePrecio;
        break;
    }
    //alert("En clickSwitch la descripcionP: " + DescripcionP);
    document.getElementById("Encabezado").innerHTML = DescripcionP;
}

function AsignaParametro(Control)
{
    //alert("Asigna Parametro:" + Control);
    //DescripcionP        = "Con Todo";
    gCatsup             = Control;
    gCebolla            = Control;
    gChamoy             = Control;
    gChile              = Control;
    gChipotle           = Control;
    gCarnes             = Control;
    gCrema              = Control;
    gCremaBatida        = Control;
    gJitomate           = Control;
    gLechuga            = Control;
    gMayonesa           = Control;
    gMiguelito          = Control;
    gMostaza            = Control;
    gPan                = Control;
    gQueso              = Control;
    gRajas              = Control;
    gSalsa              = Control;
    gOrden              = Control;
    gPaquete            = Control;
    gTEnchilada         = Control;
    gTBurrito           = Control;
    gTHotDog            = Control;
    gTHamburguesa       = Control;
    gTTorta             = Control;
    clickSwitch(Control);
}

function onConfigura(Cadena)
{
    //alert("onConfig" + Cadena);
    //console.log("onConfig:" + Cadena);
    var Cadena2         = 'Param' + Cadena;
    DescripcionP = Cadena + " con Todo ";
    var Encabezado      = "<center><div id='Encabezado' style='color: White'>" + DescripcionP + "</div><br></center>";
    var BotonActualizar = "<center><button value='Agregar' class='btn btn-primary' onclick='AgregaCarrito(" + 
                          Cadena2 + 
                          ")'>Agregar</button></center><br>";
    switch(Cadena)
    {
        case "Burrito":
        console.log(document.getElementById("Plantilla").innerHTML = Encabezado + Burrito + BotonActualizar);
        AsignaParametro("Burrito");
        break;
        case "Chilaquiles":
        console.log(document.getElementById("Plantilla").innerHTML = Encabezado + Chilaquiles + BotonActualizar);
        //DescripcionP = "Chilaquiles con Todo";
        AsignaParametro("Chilaquiles");
        break;
        case "Enchilada": 
        console.log(document.getElementById("Plantilla").innerHTML = Encabezado + Enchilada + BotonActualizar);
        AsignaParametro("Enchilada");
        break;
        case "Hamburguesa":
        console.log(document.getElementById("Plantilla").innerHTML = Encabezado + Hamburguesa + BotonActualizar);
        AsignaParametro("Hamburguesa");
        break;
        case "HotDog":
        console.log(document.getElementById("Plantilla").innerHTML = Encabezado + HotDog + BotonActualizar);
        AsignaParametro("HotDog");
        break;
        case "Molletes":
        console.log(document.getElementById("Plantilla").innerHTML = Encabezado + Molletes + BotonActualizar);
        AsignaParametro("Molletes");
        break;
        case "Pambazo":
        console.log(document.getElementById("Plantilla").innerHTML = Encabezado + Pambazo + BotonActualizar);
        AsignaParametro("Pambazo");
        break;
        case "Quesadilla":
        console.log(document.getElementById("Plantilla").innerHTML = Encabezado + Quesadilla + BotonActualizar);
        AsignaParametro("Quesadilla");
        break;
        case "Sincronizada":
        console.log(document.getElementById("Plantilla").innerHTML = Encabezado + Sincronizada + BotonActualizar);
        AsignaParametro("Sincronizada");
        break;
        case "Sopa":
        console.log(document.getElementById("Plantilla").innerHTML = Encabezado + Sopa + BotonActualizar);
        AsignaParametro("Sopa");
        break;
        case "TacoDorado":
        console.log(document.getElementById("Plantilla").innerHTML = Encabezado + TacoDorado + BotonActualizar);
        AsignaParametro("TacoDorado");
        break;
        case "Torta":
        console.log(document.getElementById("Plantilla").innerHTML = Encabezado + Torta + BotonActualizar);
        AsignaParametro("Torta");
        break;
    }
}