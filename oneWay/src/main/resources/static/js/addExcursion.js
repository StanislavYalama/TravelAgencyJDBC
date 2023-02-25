var optionExcursion = document.getElementsByName("optionExcursion");
var excursionList = JSON.parse('${excursionsGson}');

function fillSelectByExcursions()
{
    var selectLocation = document.getElementById("addExcursion_location");
    var locationId = selectLocation[selectLocation.selectedIndex].value;
    console.log(locationId);
    console.log(excursionList);
    console.log(optionExcursion);

    excursionList.foreach(function(el, index) => {
        if(el.locationId == locationId){
            optionExcursion[index].style.visibility="visible";
        }
    });

}