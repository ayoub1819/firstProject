document.getElementsByClassName("alert-body")[0].remove();
$(".envoyer_offre_modale_button").on("click",function (){
    console.log("trigger click");
    let idOffre = $(this).attr('id');
    $("#apell-offre-id").val(idOffre);
});

$("#submit-send-appel-offre").on("click",function (){
    let idAppelOffre = $("#apell-offre-id").val();
    let description = $("#detail-offre").val();
    let montant = $("#montant").val();
    let offre = {descrption:description,montant:montant,idAppelOffre:idAppelOffre};
    $.ajax({
        url: '/envoyerOffre',
        dataType: 'html',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(offre),
        processData: false,
        success: function( data, textStatus, jQxhr ){
            Swal.fire({
                icon: 'success',
                title: 'successé!',
                text: 'votre offre à été bien publier.',
                customClass: {
                    confirmButton: 'btn btn-success'
                }
            }).then(function (result) {
                window.location.reload();
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








//form.addEventListener('submit', handleSubmit);