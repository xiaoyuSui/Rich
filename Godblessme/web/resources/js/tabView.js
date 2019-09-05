/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var oP = document.getElementById("options");

var oL = document.getElementById("text");

var oLi = oP.getElementsByTagName("li");

var oBox = oL.getElementsByTagName('li');

for(var i=0;i<oLi.length;i++){

    oLi[i].index = i;

    oLi[i].onclick=function(){

        for(var j=0;j<oLi.length;j++){

        	oLi[j].className="";

        	oBox[j].style.display="none";

        }

        oLi[this.index].className="active";

        oBox[this.index].style.display="block";

    }

}