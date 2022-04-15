$(".signaler-panne").on("click",function() {
    let id_ressource = $(this).attr("id");
    let isImp = $(this).attr("ress-type") == "imp";
    console.log(isImp);
    console.log($("#panne-ordre"));
    console.log($("#panne-ordre").selectedIndex);
    $("#id_ressource").val(id_ressource);
    if (isImp){
        $(".panne-order-div").addClass("d-none");
        $("#panne-ordre option:eq(0)").prop("selected",false);
        $("#panne-ordre option:eq(2)").prop("selected",true);
    }else $(".panne-order-div").removeClass("d-none");
});


function signalerPanne(event) {
    event.preventDefault();

    const data = new FormData(event.target);
    const id_ressource = data.get('id_ressource');
    const ordre = data.get('ordre');
    const dateApparition = data.get('dateApparition');
    const frequence = data.get('frequence');
    const explication = data.get('explication');
    let panne = {ressource:{id:id_ressource},
                    ordre:ordre,
                    dateApparition:dateApparition,
                    frequence:frequence,
                    explication:explication};
    /*const isForDep = data.get('depRes');
    console.log(isForDep);*/
    console.log(panne);
        $.ajax({
            url: "/addPanne",
            dataType: 'text',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(panne),
            processData: false,
            success: function( data, textStatus, jQxhr ){
                Swal.fire({
                    icon: 'success',
                    title: 'successé!',
                    text: 'votre reclamation à été bien envoyer.',
                    customClass: {
                        confirmButton: 'btn btn-success'
                    }
                }).then(function (result) {
                    window.location.reload();
                });
                //$(idForm).append( '<input type="hidden" name="id_besoin" value="'+data+'">');
            },
            error: function( jqXhr, textStatus, errorThrown ){
                toastr['error']('l\'enregistrement a echoué.', 'Echec!', {
                    closeButton: true,
                    tapToDismiss: false,
                    rtl: false
                });
            }
        });

}
$("form").on("submit",signalerPanne);