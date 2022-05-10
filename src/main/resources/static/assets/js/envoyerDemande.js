$(".send-demand-modal-button").on("click",function (){
    console.log("trigger click");
    let idDepartement = $(this).attr('id');
    $.ajax({
        url: '/envoyerDemandeModalForm',
        dataType: 'html',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(idDepartement),
        processData: false,
        success: function( data, textStatus, jQxhr ){
            console.log( $("#update-besoin-modal-body"));
            $("#send-demande-modal-body").html(data);

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

$("#submit-send-demand").on("click",function (){
    console.log("trigger click");
    let idDepartement = $("#demande-id-deparetement").val();
    console.log("id_deparetement: "+idDepartement);
    $.ajax({
        url: '/envoyerDemande',
        dataType: 'html',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(idDepartement),
        processData: false,
        success: function( data, textStatus, jQxhr ){
            Swal.fire({
                icon: 'success',
                title: 'successé!',
                text: 'votre demande à été bien envoyer.',
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