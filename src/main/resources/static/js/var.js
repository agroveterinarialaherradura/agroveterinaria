/* 
 * Contiene exclusivamente las variables utilizadas en la aplicación.
 * Contiene los Ingredientes de cada producto
 * y algunos contenedores.
 *  onclick='Agregar(ParamFriaCaiente)'
 *  onchange='onChangeSwitch(value)'
 *  onload='onChangeSwitch(value)'
 *  clickSwitch
 */

var Catsup =   "<div class='switch2'>"+
               "<input type='radio' name='Catsup' value='CCatsup' id='CCatsup' class='switch2-input' onclick='clickSwitch(gCatsup)' checked>"+
               "<label for='CCatsup' class='switch2-label'>Catsup</label>"+
               "<input type='radio' name='Catsup' value='SCatsup' id='SCatsup' class='switch2-input' onclick='clickSwitch(gCatsup)'>"+
               "<label for='SCatsup' class='switch2-label'>Sin Catsup</label>"+
               "</div>";
var Cebolla =  "<div class='switch2'>"+
               "<input type='radio' name='Cebolla' value='CCebolla' id='CCebolla' class='switch2-input' onclick='clickSwitch(gCebolla)' checked>"+
               "<label for='CCebolla' class='switch2-label'>Cebolla</label>"+
               "<input type='radio' name='Cebolla' value='SCebolla' id='SCebolla' class='switch2-input' onclick='clickSwitch(gCebolla)'>"+
               "<label for='SCebolla' class='switch2-label'>Sin Cebolla</label>"+
               "</div>";
var Chamoy =   "<div class='switch2'>"+
               "<input type='radio' name='Chamoy' value='CChamoy' id='CChamoy' class='switch2-input' onclick='clickSwitch(gChamoy)' checked>"+
               "<label for='CChamoy' class='switch2-label'>Chamoy</label>"+
               "<input type='radio' name='Chamoy' value='SChamoy' id='SChamoy' class='switch2-input' onclick='clickSwitch(gChamoy)'>"+
               "<label for='SChamoy' class='switch2-label'>Sin Chamoy</label>"+
               "</div>";
var Chile =    "<div class='switch2'>"+
               "<input type='radio' name='Chile' value='CChile' id='CChile' class='switch2-input' onclick='clickSwitch(gChile)' checked>"+
               "<label for='CChile' class='switch2-label'>Chile</label>"+
               "<input type='radio' name='Chile' value='SChile' id='SChile' class='switch2-input' onclick='clickSwitch(gChile)'>"+
               "<label for='SChile' class='switch2-label'>Sin Chile</label>"+
               "</div>";
var Chipotle = "<div class='switch2'>"+
               "<input type='radio' name='Chipotle' value='CChipotle' id='CChipotle' class='switch2-input' onclick='clickSwitch(gChipotle)' checked>"+
               "<label for='CChipotle' class='switch2-label'>Chipotle</label>"+
               "<input type='radio' name='Chipotle' value='SChipotle' id='SChipotle' class='switch2-input' onclick='clickSwitch(gChipotle)'>"+
               "<label for='SChipotle' class='switch2-label'>Sin Chipotle</label>"+
               "</div>";
var Carnes =   "<div class='switch3'>"+
               "<input type='radio' name='Carnes' value='Res' id='Res' class='switch3-input' onclick='clickSwitch(gCarnes)' checked>"+
               "<label for='Res' class='switch3-label'>Res</label>"+
               "<input type='radio' name='Carnes' value='Pollo' id='Pollo' class='switch3-input' onclick='clickSwitch(gCarnes)'>"+
               "<label for='Pollo' class='switch3-label'>Pollo</label>"+
               "<input type='radio' name='Carnes' value='Sirloin' id='Sirloin' class='switch3-input' onclick='clickSwitch(gCarnes)'>"+
               "<label for='Sirloin' class='switch3-label'>Sirloin</label>"+
               "</div>";
var Crema    = "<div class='switch2'>"+
               "<input type='radio' name='Crema' value='CCrema' id='CCrema' class='switch2-input' onclick='clickSwitch(gCrema)' checked>"+
               "<label for='CCrema' class='switch2-label'>Crema</label>"+
               "<input type='radio' name='Crema' value='SCrema' id='SCrema' class='switch2-input' onclick='clickSwitch(gCrema)'>"+
               "<label for='SCrema' class='switch2-label'>Sin Crema</label>"+
               "</div>";
var CremaBatida="<div class='switch2'>"+
               "<input type='radio' name='CremaBatida' value='CCremaBatida' id='CCremaBatida' class='switch2-input' onclick='clickSwitch(gCremaBatida)' checked>"+
               "<label for='CCremaBatida' class='switch2-label'>Crema Batida</label>"+
               "<input type='radio' name='CremaBatida' value='SCremaBatida' id='SCremaBatida' class='switch2-input' onclick='clickSwitch(gCremaBatida)'>"+
               "<label for='SCremaBatida' class='switch2-label'>Sin Crema Batida</label>"+
               "</div>";
var Jitomate = "<div class='switch2'>"+
               "<input type='radio' name='Jitomate' value='CJitomate' id='CJitomate' class='switch2-input' onclick='clickSwitch(gJitomate)' checked>"+
               "<label for='CJitomate' class='switch2-label'>Jitomate</label>"+
               "<input type='radio' name='Jitomate' value='SJitomate' id='SJitomate' class='switch2-input' onclick='clickSwitch(gJitomate)'>"+
               "<label for='SJitomate' class='switch2-label'>Sin Jitomate</label>"+
               "</div>";
var Lechuga =  "<div class='switch2'>"+
               "<input type='radio' name='Lechuga' value='CLechuga' id='CLechuga' class='switch2-input' onclick='clickSwitch(gLechuga)' checked>"+
               "<label for='CLechuga' class='switch2-label'>Lechuga</label>"+
               "<input type='radio' name='Lechuga' value='SLechuga' id='SLechuga' class='switch2-input' onclick='clickSwitch(gLechuga)'>"+
               "<label for='SLechuga' class='switch2-label'>Sin Lechuga</label>"+
               "</div>";
var Mayonesa = "<div class='switch2'>"+
               "<input type='radio' name='Mayonesa' value='CMayonesa' id='CMayonesa' class='switch2-input' onclick='clickSwitch(gMayonesa)' checked>"+
               "<label for='CMayonesa' class='switch2-label'>Mayonesa</label>"+
               "<input type='radio' name='Mayonesa' value='CMayonesa' id='SMayonesa' class='switch2-input' onclick='clickSwitch(gMayonesa)'>"+
               "<label for='SMayonesa' class='switch2-label'>Sin Mayo</label>"+
               "</div>";
var Miguelito= "<div class='switch2'>"+
               "<input type='radio' name='Miguelito' value='CMiguelito' id='CMiguelito' class='switch2-input' onclick='clickSwitch(gMiguelito)' checked>"+
               "<label for='CMiguelito' class='switch2-label'>Miguelito</label>"+
               "<input type='radio' name='Miguelito' value='SMiguelito' id='SMiguelito' class='switch2-input' onclick='clickSwitch(gMiguelito)'>"+
               "<label for='SMiguelito' class='switch2-label'>Sin Miguelito</label>"+
               "</div>";
var Mostaza =  "<div class='switch2'>"+
               "<input type='radio' name='Mostaza' value='CMostaza' id='CMostaza' class='switch2-input' onclick='clickSwitch(gMostaza)' checked>"+
               "<label for='CMostaza' class='switch2-label'>Mostaza</label>"+
               "<input type='radio' name='Mostaza' value='SMostaza' id='SMostaza' class='switch2-input' onclick='clickSwitch(gMostaza)'>"+
               "<label for='SMostaza' class='switch2-label'>Sin Mostaza</label>"+
               "</div>";
var Pan =      "<div class='switch2'>"+
               "<input type='radio' name='Pan' value='CPan' id='CPan' class='switch2-input' onclick='clickSwitch(gPan)' checked>"+
               "<label for='CPan' class='switch2-label'>Pan</label>"+
               "<input type='radio' name='Pan' value='SPan' id='SPan' class='switch2-input' onclick='clickSwitch(gPan)'>"+
               "<label for='SPan' class='switch2-label'>Sin Pan</label>"+
               "</div>";
var Queso =    "<div class='switch2'>"+
               "<input type='radio' name='Queso' value='CQueso' id='CQueso' class='switch2-input' onclick='clickSwitch(gQueso)' checked>"+
               "<label for='CQueso' class='switch2-label'>Queso</label>"+
               "<input type='radio' name='Queso' value='SQueso' id='SQueso' class='switch2-input' onclick='clickSwitch(gQueso)'>"+
               "<label for='SQueso' class='switch2-label'>Sin Queso</label>"+
               "</div>";
var Rajas =    "<div class='switch2'>"+
               "<input type='radio' name='Rajas' value='CRajas' id='CRajas' class='switch2-input' onclick='clickSwitch(gRajas)' checked>"+
               "<label for='CRajas' class='switch2-label'>Rajas</label>"+
               "<input type='radio' name='Rajas' value='SRajas' id='SRajas' class='switch2-input' onclick='clickSwitch(gRajas)'>"+
               "<label for='SRajas' class='switch2-label'>Sin Rajas</label>"+
               "</div>";
var Salsa =    "<div class='switch2'>"+
               "<input type='radio' name='Salsa' value='CSalsa' id='CSalsa' class='switch2-input' onclick='clickSwitch(gSalsa)' checked>"+
               "<label for='CSalsa' class='switch2-label'>Salsa</label>"+
               "<input type='radio' name='Salsa' value='SSalsa' id='SSalsa' class='switch2-input' onclick='clickSwitch(gSalsa)'>"+
               "<label for='SSalsa' class='switch2-label'>Sin Salsa</label>"+
               "</div>";

var Orden  =   "<div class='switch2'>"+
               "<input type='radio' name='Orden' value='COrden' id='COrden' class='switch2-input' onclick='clickSwitch(gOrden)' checked>"+
               "<label for='COrden' class='switch2-label'>Orden</label>"+
               "<input type='radio' name='Orden' value='SOrden' id='SOrden' class='switch2-input' onclick='clickSwitch(gOrden)'>"+
               "<label for='SOrden' class='switch2-label'>Individual</label>"+
               "</div>";
var Paquete =  "<div class='switch2'>"+
               "<input type='radio' name='Paquete' value='CPaquete' id='CPaquete' class='switch2-input' onclick='clickSwitch(gPaquete)' checked>"+
               "<label for='CPaquete' class='switch2-label'>Paquete</label>"+
               "<input type='radio' name='Paquete' value='SPaquete' id='SPaquete' class='switch2-input' onclick='clickSwitch(gPaquete)'>"+
               "<label for='SPaquete' class='switch2-label'>Individual</label>"+
               "</div>";

var TEnchilada="<div class='switch3'>"+
               "<input type='radio' name='TEnchilada' value='Suizas' id='Suizas' class='switch3-input' onclick='clickSwitch(gTEnchilada)' checked>"+
               "<label for='Suizas' class='switch3-label'>Suizas</label>"+
               "<input type='radio' name='TEnchilada' value='Verdes' id='Verdes' class='switch3-input' onclick='clickSwitch(gTEnchilada)'>"+
               "<label for='Verdes' class='switch3-label'>Verdes</label>"+
               "<input type='radio' name='TEnchilada' value='Mole' id='Mole' class='switch3-input' onclick='clickSwitch(gTEnchilada)'>"+
               "<label for='Mole' class='switch3-label'>Mole</label>"+
               "</div>";
var TBurrito = "<div class='switch4'>"+
               "<input type='radio' name='TBurritos' value='Pollo' id='Pollo' class='switch4-input' onclick='clickSwitch(gTBurrito)' checked>"+
               "<label for='Pollo' class='switch4-label'>Pollo</label>"+
               "<input type='radio' name='TBurritos' value='Bistec' id='Bistec' class='switch4-input' onclick='clickSwitch(gTBurrito)'>"+
               "<label for='Bistec' class='switch4-label'>Bistec</label>"+
               "<input type='radio' name='TBurritos' value='Pastor' id='Pastor' class='switch4-input' onclick='clickSwitch(gTBurrito)'>"+
               "<label for='Pastor' class='switch4-label'>Pastor</label>"+
               "<input type='radio' name='TBurritos' value='Longaniza' id='Longaniza' class='switch4-input' onclick='clickSwitch(gTBurrito)'>"+
               "<label for='Longaniza' class='switch4-label'>Longaniza</label>"+
               "</div>";
var THotDog =  "<div class='switch5'>"+
               "<input type='radio' name='THotDog' value='Sencillo' id='Sencillo' class='switch5-input' onclick='clickSwitch(gTHotDog)' checked>"+
               "<label for='Sencillo' class='switch5-label'>Sencillo</label>"+
               "<input type='radio' name='THotDog' value='Tocino' id='Tocino' class='switch5-input' onclick='clickSwitch(gTHotDog)'>"+
               "<label for='Tocino' class='switch5-label'>Tocino</label>"+
               "<input type='radio' name='THotDog' value='Quesillo' id='Quesillo' class='switch5-input' onclick='clickSwitch(gTHotDog)'>"+
               "<label for='Quesillo' class='switch5-label'>Quesillo</label>"+
               "<input type='radio' name='THotDog' value='TocinoQueso' id='TocinoQueso' class='switch5-input' onclick='clickSwitch(gTHotDog)'>"+
               "<label for='TocinoQueso' class='switch5-label'>Tocino y Queso</label>"+
               "<input type='radio' name='THotDog' value='Hawaiano' id='Hawaiano' class='switch5-input' onclick='clickSwitch(gTHotDog)'>"+
               "<label for='Hawaiano' class='switch5-label'>Hawaiano</label>"+
               "</div>";
var THamburguesa="<div class='switch5'>"+
               "<input type='radio' name='THamburguesa' value='Sencilla' id='Sencilla' class='switch5-input' onclick='clickSwitch(gTHamburguesa)' checked>"+
               "<label for='Sencilla' class='switch5-label'>Sencilla</label>"+
               "<input type='radio' name='THamburguesa' value='Tocino' id='Tocino' class='switch5-input' onclick='clickSwitch(gTHamburguesa)'>"+
               "<label for='Tocino' class='switch5-label'>Tocino</label>"+
               "<input type='radio' name='THamburguesa' value='Quesillo' id='Quesillo' class='switch5-input' onclick='clickSwitch(gTHamburguesa)'>"+
               "<label for='Quesillo' class='switch5-label'>Quesillo</label>"+
               "<input type='radio' name='THamburguesa' value='TocinoQueso' id='TocinoQueso' class='switch5-input' onclick='clickSwitch(gTHamburguesa)'>"+
               "<label for='TocinoQueso' class='switch5-label'>Tocino y Queso</label>"+
               "<input type='radio' name='THamburguesa' value='Hawaiano' id='Hawaiano' class='switch5-input' onclick='clickSwitch(gTHamburguesa)'>"+
               "<label for='Hawaiano' class='switch5-label'>Hawaiano</label>"+
               "</div>";
var TTorta  =  "<div class='switch6'>"+
               "<input type='radio' name='TTorta' value='Res' id='Res' class='switch6-input' onclick='clickSwitch(gTTorta)' checked>"+
               "<label for='Res' class='switch6-label'>Res</label>"+
               "<input type='radio' name='TTorta' value='Pollo' id='Pollo' class='switch6-input' onclick='clickSwitch(gTTorta)'>"+
               "<label for='Pollo' class='switch6-label'>Pollo</label>"+
               "<input type='radio' name='TTorta' value='Pastor' id='Pastor' class='switch6-input' onclick='clickSwitch(gTTorta)'>"+
               "<label for='Pastor' class='switch6-label'>Pastor</label>"+
               "<input type='radio' name='TTorta' value='Salchicha' id='Salchicha' class='switch6-input' onclick='clickSwitch(gTTorta)'>"+
               "<label for='Salchicha' class='switch6-label'>Salchicha</label>"+
               "<input type='radio' name='TTorta' value='Pierna' id='Pierna' class='switch6-input' onclick='clickSwitch(gTTorta)'>"+
               "<label for='Pierna' class='switch6-label'>Pierna</label>"+               
               "<input type='radio' name='TTorta' value='Cubana' id='Cubana' class='switch6-input' onclick='clickSwitch(gTTorta)'>"+
               "<label for='Cubana' class='switch6-label'>Cubana</label>"+
               "</div>";

var gContador= 1;
//var RegFilas = ['N','Cantidad','Descripcion','Precio'];
var FilasTablas = "";
var Folio       = "";
var Suma        = 0;
var EncabezadoTabla = "";
var FilaCompraTabla = "";//"<tr><td>" + Tabla[0][2] + "</td></tr>";

var ParamBurrito        = "Burrito";
var ParamChilaquiles    = "Chilaquiles";
var ParamEnchilada      = "Enchilada";
var ParamHamburguesa    = "Hamburguesa";
var ParamHotDog         = "HotDog";
var ParamMolletes       = "Mollete";
var ParamPambazo        = "Pambazo";
var ParamQuesadilla     = "Quesadilla";
var ParamSincronizada   = "Sincronizada";
var ParamSopa           = "Sopa";
var ParamTacoDorado     = "TacoDorado";
var ParamTorta          = "Torta";

var cveProducto         = "Ninguno";
var cvePrecio           = 0;
var cveCantidad         = 0;

var Adicional           = "";
var TextoEncab          = "Personalice su(s) ";
var DescripcionP        = "";
    
var gCatsup             = "Catsup";
var gCebolla            = "Cebolla";
var gChamoy             = "Chamoy";
var gChile              = "Chile";
var gChipotle           = "Chipotle";
var gCarnes             = "Carnes";
var gCrema              = "Crema";
var gCremaBatida        = "CremaBatida";
var gJitomate           = "Jitomate";
var gLechuga            = "Lechuga";
var gMayonesa           = "Mayonesa";
var gMiguelito          = "Miguelito";
var gMostaza            = "Mostaza";
var gPan                = "Pan";
var gQueso              = "Queso";
var gRajas              = "Rajas";
var gSalsa              = "Salsa";
var gOrden              = "Orden";
var gPaquete            = "Paquete";
var gTEnchilada         = "TEnchilada";
var gTBurrito           = "TBurrito";
var gTHotDog            = "THotDog";
var gTHamburguesa       = "THamburguesa";
var gTTorta             = "TTorta";

var gBurrito            = "Burrito";
var gChilaquiles        = "Chilaquiles";
var gEnchiladas         = "Enchilada";
var gHamburguesa        = "Hamburguesa";
var gHotDog             = "HotDog";
var gMolletes           = "Molletes";
var gPambazo            = "Pambazo";
var gQuesadilla         = "Quesadilla";
var gSincronizada       = "Sincronizada";
var gSopa               = "Sopa";
var gTacoDorado         = "TacoDorado";
var gTorta              = "Torta";

var Burrito     = Orden + TBurrito + Lechuga + Crema + Queso;
var Chilaquiles = Crema + Queso + Pan;
var Enchilada   = Orden + TEnchilada + Crema + Queso + Cebolla + Salsa;
var Hamburguesa = THamburguesa + Carnes + Crema + Salsa;
var HotDog      = Orden + THotDog + Mayonesa + Mostaza + Catsup + Jitomate + Chile;
var Molletes    = Orden + Crema + Queso + Cebolla + Salsa;
var Pambazo     = Lechuga + Crema + Queso;
var Quesadilla  = Orden + Lechuga + Crema + Queso;
var Sincronizada= Catsup + Mayonesa + Jitomate + Chile;
var Sopa        = Chipotle + Rajas;
var TacoDorado  = Orden + Lechuga + Crema + Queso;
var Torta       = TTorta + Chipotle + Rajas + Lechuga + Jitomate;

var Resultado   = document.getElementById("Cuerpo");

function startTime(){
    today=new Date();
    h=today.getHours();
    m=today.getMinutes();
    s=today.getSeconds();
    m=checkTime(m);
    s=checkTime(s);
    document.getElementById('reloj').innerHTML=h+":"+m+":"+s;
    t=setTimeout('startTime()',500);
}

function EnviaPago(){
    var xmlhttp;
    alert("Suma: " + Suma);
    if(window.XMLHttpRequest){
       xmlhttp = new XMLHttpRequest();
    }else{
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function(){
        if(xmlhttp.readyState === 4 && xmlhttp.status === 200){
            var Mensaje = xmlhttp.return;
            alert("Mensajes " + xmlhttp.return);
            Resultado = "";
            //load(Cadena);
            document.getElementById("Tabla").innerHTML = "";
            document.getElementById("Cuerpo").innerHTML = "";
        }
    }
    var Cadena = "Suma="+Suma;
    xmlhttp.open("POST", "Recibir.php", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send(Cadena);
}