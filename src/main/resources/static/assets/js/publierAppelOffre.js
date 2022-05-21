$(".send-appel-offre-modal-button").on("click",function (){
    $.ajax({
        url: '/envoyerAppelOffreModalForm',
        dataType: 'html',
        type: 'post',
        contentType: 'application/json',
        processData: false,
        success: function( data, textStatus, jQxhr ){
            console.log( $("#send-appel-offre-modal-body"));
            $("#send-appel-offre-modal-body").html(data);
            document.getElementsByClassName("alert-body")[0].remove();
            console.log("ffdfdf"+document.getElementsByClassName("alert-body")[0]);

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

$("#submit-send-appel-offre").on("click",function (){
    $.ajax({
        url: '/postappeloffre',
        dataType: 'html',
        type: 'post',
        contentType: 'application/json',
        processData: false,
        success: function( data, textStatus, jQxhr ){
            Swal.fire({
                icon: 'success',
                title: 'successé!',
                text: 'l\'appel d\'offre à été bien publier.',
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