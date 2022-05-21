



function handleSubmit(event) {
    event.preventDefault();

    const data = new FormData(event.target);
    const email = data.get('email');
    const societe = data.get('societe');
    const lieu = data.get('lieu');
    const gerant = data.get('gerant');
    const telephone = data.get('telephone');
    const compte = {username:email,email:email}
    const fournisseur = {societe:societe,lieu:lieu,gerant:gerant,tele:telephone,compte:compte};

    $.ajax({
        url: "/ajouterFournisseur",
        dataType: 'text',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(fournisseur),
        processData: false,
        success: function( data, textStatus, jQxhr ){
            Swal.fire({
                icon: 'success',
                title: 'successé!',
                text: 'Fournisseur à été bien ajouter.',
                customClass: {
                    confirmButton: 'btn btn-success'
                }
            }).then(function (result) {
                window.location.reload();
            });

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
const form = document.querySelector('form');
$("form").on("submit",handleSubmit);

$(".blockFour").on("click",function (){
    console.log("trigger click");
    let username = $(this).attr('id');
    $("#blockedUsername").val(username);
});
$("#submit-block-fournisseur").on("click",function (){

    let motif = $("#motif").val();
    let username = $("#blockedUsername").val();
    let compte = {username:username};
    let fournisseur = {compte:compte, motifDeBlockage:motif};
    $.ajax({
        url: '/blockFournisseur',
        dataType: 'html',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(fournisseur),
        processData: false,
        success: function( data, textStatus, jQxhr ){
            Swal.fire({
                icon: 'success',
                title: 'successé!',
                text: 'fournisseur à éié blocké.',
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