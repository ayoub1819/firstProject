$(".demande-resume").on("click",function (){
    console.log("trigger click");
    let idDemande = $(this).attr('id');
    $.ajax({
        url: '/suivreDemandeDetail',
        dataType: 'html',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(idDemande),
        processData: false,
        success: function( data, textStatus, jQxhr ){
            console.log( $("#update-besoin-modal-body"));
            $("#send-demande-modal-body").html(data);
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








//form.addEventListener('submit', handleSubmit);