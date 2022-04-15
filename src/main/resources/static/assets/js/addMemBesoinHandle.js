$(".edit-besoin-modal-button").on("click",function (){
    let idBesoin = $(this).attr('id');
    $.ajax({
        url: '/editBesoinModalForm',
        dataType: 'html',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(idBesoin),
        processData: false,
        success: function( data, textStatus, jQxhr ){
            console.log( $("#update-besoin-modal-body"));
            $("#update-besoin-modal-body").html(data);
            // form repeater jquery
            $('.invoice-repeater, .repeater-default').repeater({
                show: function () {
                    $(this).slideDown();
                    // Feather Icons
                    if (feather) {
                        feather.replace({ width: 14, height: 14 });
                    }
                },
                hide: function (deleteElement) {
                    if (confirm('Are you sure you want to delete this element?')) {
                        $(this).slideUp(deleteElement);
                    }
                }
            });

        },
        error: function( jqXhr, textStatus, errorThrown ){
            Swal.fire({
                icon: 'error',
                title: 'error',
                text: 'erreur inconue.',
                customClass: {
                    confirmButton: 'btn btn-success'
                }
            })
        }
    });
})



function handleSubmit(event) {
    event.preventDefault();
    let isGestionDesBesoin =  $(this).attr('besoin-manager') == 'true';
    let action = (isGestionDesBesoin)? "/addManagedBesoin":"/addBesoin";
    let isForDep = $(this).attr('id') == 'aff-dep';
    let idForm = (isForDep)? "#aff-dep":"#aff-moi"
    let value = $(this).repeaterVal();
    const data = new FormData(event.target);
    const id_dep = data.get('id_dep');
    const id_mem_dep = data.get('id_mem_dep');
    const id_besoin = data.get('id_besoin');
    const etat_besoin= data.get('etat_besoin');
    value.deparetement = {id_departement : id_dep};
    value.membre = isForDep? null:{id : id_mem_dep};
    value.id = id_besoin;
    value.etat = etat_besoin;
    delete value.id_dep;
    delete value.etat_besoin;
    delete value.id_besoin;
    delete value.id_mem_dep;
    let rsc ="";
    let error=[];
    /*const isForDep = data.get('depRes');
    console.log(isForDep);*/
    value.ressource.forEach(function(item){
        let type = (item.type == 1)? "pc":"imp";
        if (item.type == 0) type = "";
        if (type!='pc' && type!='imp') {
            error.push("aucun type de resouce est selctioné");
            return 0;
        }
        let quantite = item.quantite;
        let ecran = item.ecran
        let cpu = item.cpu;
        let ram = item.ram;
        let resolition = item.resolition
        let vitesse = item.vitesse;
        let disque_dur = item.disque_dur
        if (type == "imp"){
            if (isNaN(parseInt(resolition)) || parseInt(quantite)<=0) {
                error.push("la resoulution saisi est invalide");
                return 0;
            }
            if (isNaN(parseInt(vitesse)) || parseInt(quantite)<=0) {
                error.push("la vitess saisi est invalide");
                return 0;
            }
        }
        if (type == "pc"){
            if (isNaN(parseInt(ram)) || parseInt(ram)<=0){
                error.push("la tail de ram saisi est invalide");
                return 0;
            }
            if (isNaN(parseInt(disque_dur)) || parseInt(quantite)<=0) {
                error.push("la taille du disque saisi est invalide");
                return 0;
            }
            if (isNaN(parseInt(quantite)) || parseInt(quantite)<=0) {
                error.push("la quantité saisi est invalide");
                return 0;
            }
            if (ecran == "") {
                error.push("la quantité saisi est invalide");
                return 0;
            }
            if (cpu == "") {
                error.push("la quantité saisi est invalide");
                return 0;
            }
        }


        if (type == "pc") rsc += type+","+quantite+","+ecran+","+cpu+","+ram+","+disque_dur+";";
        else rsc += type+","+quantite+","+resolition+","+vitesse+";";
    })
    value.ressource = rsc;
    console.log(error);
    console.log(value);
    if (error.length == 0)
        $.ajax({
            url: action,
            dataType: 'text',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(value),
            processData: false,
            success: function( data, textStatus, jQxhr ){
                toastr['success']('le besoin est enregistrer avec successé.', 'Success!', {
                    closeButton: true,
                    tapToDismiss: false,
                    rtl: false
                });
                $(idForm).append( '<input type="hidden" name="id_besoin" value="'+data+'">');
            },
            error: function( jqXhr, textStatus, errorThrown ){
                toastr['error']('l\'enregistrement a echoué.', 'Echec!', {
                    closeButton: true,
                    tapToDismiss: false,
                    rtl: false
                });
            }
        });
    else
        toastr['error'](error.pop(), 'Echec!', {
            closeButton: true,
            tapToDismiss: false,
            rtl: false
        });
}
const form = document.querySelector('form');
$("form").on("submit",handleSubmit);
//form.addEventListener('submit', handleSubmit);