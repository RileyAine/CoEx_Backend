if ($args[0] -eq $null) {
    echo "Usage: .\docker_push.ps1 ^<TagName^>"
    Exit
}

$confirmation = Read-Host "Did you update the version in pom.xml AND Dockerfile?"
if ($confirmation -eq 'y') {
    # proceed
} else {
    Write-Host 'Well then do that.'
    Exit
}

echo "Building production front end jar"

mvn clean package -DskipTests
java -jar target/CoEx_Backend-$($args[0])-SNAPSHOT.jar

echo "Checking docker process"
$processes = Get-Process "*docker desktop*"
if ($processes.Count -lt 1){
    Start-Process "C:\Program Files\Docker\Docker\Docker Desktop.exe"
    echo "Starting Docker"
} else {echo "Docker running"}

echo "Building image $($args[0])"
docker build --tag rileysaur/coex_backend:$($args[0]) .

echo "Pushing image to repo"
docker push rileysaur/coex_backend:$($args[0])